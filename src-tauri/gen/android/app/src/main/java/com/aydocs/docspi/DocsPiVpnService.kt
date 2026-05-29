package com.aydocs.docspi

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.VpnService
import android.os.Build
import android.os.ParcelFileDescriptor
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong

/**
 * DocsPI VpnService — intercepts all device traffic via Android VpnService API.
 *
 * Called from Rust via JNI through Tauri's mobile plugin bridge:
 *   startVpn(context, dns, proxyPort, bypassMode)
 *   stopVpn()
 *   isRunning()
 */
class DocsPiVpnService : VpnService() {

    companion object {
        const val CHANNEL_ID = "docspi_vpn_channel"
        const val FOREGROUND_NOTIFY_ID = 1

        private var vpnInstance: DocsPiVpnService? = null
        private var vpnFd: ParcelFileDescriptor? = null
        private var running = AtomicBoolean(false)
        private var bytesRx = AtomicLong(0)
        private var bytesTx = AtomicLong(0)

        @JvmStatic
        fun isRunning(): Boolean = running.get()

        @JvmStatic
        fun getBytesRx(): Long = bytesRx.get()

        @JvmStatic
        fun getBytesTx(): Long = bytesTx.get()

        @JvmStatic
        fun startVpn(context: Context, dns: String, proxyPort: Int, bypassMode: String): Boolean {
            val intent = Intent(context, DocsPiVpnService::class.java).apply {
                putExtra("dns", dns)
                putExtra("proxy_port", proxyPort)
                putExtra("bypass_mode", bypassMode)
            }
            context.startForegroundService(intent)
            return true
        }

        @JvmStatic
        fun stopVpn() {
            running.set(false)
            vpnFd?.close()
            vpnFd = null
            vpnInstance?.stopForeground(STOP_FOREGROUND_REMOVE)
            vpnInstance?.stopSelf()
        }
    }

    override fun onCreate() {
        super.onCreate()
        vpnInstance = this
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val dns = intent?.getStringExtra("dns") ?: "1.1.1.1"
        val proxyPort = intent?.getIntExtra("proxy_port", 0) ?: 0
        val bypassMode = intent?.getStringExtra("bypass_mode") ?: "turbo"

        val notification = buildNotification()
        startForeground(FOREGROUND_NOTIFY_ID, notification)

        if (!running.get()) {
            establishVpn(dns, proxyPort, bypassMode)
        }

        return START_STICKY
    }

    override fun onDestroy() {
        stopVpn()
        vpnInstance = null
        super.onDestroy()
    }

    private fun establishVpn(dns: String, proxyPort: Int, bypassMode: String): Boolean {
        if (running.get()) return true

        val builder = Builder().apply {
            setSession("DocsPI VPN")
            setMtu(1500)

            addAddress("10.0.0.2", 32)
            addRoute("0.0.0.0", 0)

            if (dns.isNotEmpty()) {
                addDnsServer(java.net.InetAddress.getByName(dns))
            } else {
                addDnsServer("1.1.1.1")
                addDnsServer("8.8.8.8")
            }

            if (proxyPort > 0) {
                addRoute("127.0.0.1", 32)
            }
        }

        builder.addDisallowedApplication(packageName)

        return try {
            vpnFd = builder.establish()
            if (vpnFd != null) {
                running.set(true)
                startPacketForwarder(vpnFd!!)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun startPacketForwarder(fd: ParcelFileDescriptor) {
        Thread {
            val input = FileInputStream(fd.fileDescriptor)
            val output = FileOutputStream(fd.fileDescriptor)
            val readBuf = ByteArray(65535)
            while (running.get()) {
                try {
                    val len = input.read(readBuf)
                    if (len <= 0) break

                    bytesRx.addAndGet(len.toLong())
                    output.write(readBuf, 0, len)
                    bytesTx.addAndGet(len.toLong())
                } catch (e: Exception) {
                    if (running.get()) e.printStackTrace()
                    break
                }
            }
        }.apply { isDaemon = true }.start()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "DocsPI VPN",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "DocsPI DPI Bypass VPN is running"
                setShowBadge(false)
            }
            val nm = getSystemService(NotificationManager::class.java)
            nm.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(): Notification {
        val openIntent = packageManager.getLaunchIntentForPackage(packageName)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("DocsPI VPN")
            .setContentText("DPI bypass aktif — trafik yönlendiriliyor")
            .setSmallIcon(android.R.drawable.ic_lock_idle_lock)
            .setContentIntent(pendingIntent)
            .setOngoing(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID)
        }

        return builder.build()
    }
}
