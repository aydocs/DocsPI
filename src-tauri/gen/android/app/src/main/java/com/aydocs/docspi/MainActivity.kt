package com.aydocs.docspi

import android.content.Intent
import android.net.VpnService
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        private const val VPN_REQUEST_CODE = 1001
        var vpnPermissionGranted = false
            private set
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * Trigger the VpnService permission dialog.
     * Called from Rust via Tauri command bridge.
     */
    fun requestVpnPermission() {
        val intent = VpnService.prepare(this)
        if (intent != null) {
            startActivityForResult(intent, VPN_REQUEST_CODE)
        } else {
            vpnPermissionGranted = true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == VPN_REQUEST_CODE) {
            vpnPermissionGranted = resultCode == RESULT_OK
        }
    }

    /**
     * Check whether VPN permission has been granted.
     * Called from Rust via Tauri command bridge.
     */
    fun isVpnPermissionGranted(): Boolean {
        val intent = VpnService.prepare(this)
        if (intent == null) {
            vpnPermissionGranted = true
        }
        return vpnPermissionGranted
    }
}
