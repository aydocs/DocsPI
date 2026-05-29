import NetworkExtension
import Network

/// DocsPI NEPacketTunnelProvider — intercepts all device traffic on iOS/iPadOS.
class PacketTunnelProvider: NEPacketTunnelProvider {

    private var proxyPort: UInt16 = 8080
    private var dnsAddress = "1.1.1.1"
    private var packetQueue: DispatchQueue?
    private(set) var isRunning = false

    override func startTunnel(options: [String : NSObject]? = nil) async throws {
        Self.runningInstance = self
        NSLog("[DocsPI VPN] Starting tunnel...")

        let proto = protocolConfiguration as? NETunnelProviderProtocol
        if let config = proto?.providerConfiguration {
            if let port = config["proxy_port"] as? UInt16 {
                proxyPort = port
            }
            if let dns = config["dns"] as? String {
                dnsAddress = dns
            }
        }

        let settings = NEPacketTunnelNetworkSettings(tunnelRemoteAddress: "127.0.0.1")
        settings.mtu = 1500 as NSNumber

        let ipv4 = NEIPv4Settings(addresses: ["10.0.0.2"], subnetMasks: ["255.255.255.255"])
        ipv4.includedRoutes = [NEIPv4Route.default()]
        ipv4.excludedRoutes = [
            NEIPv4Route(destinationAddress: "10.0.0.0", subnetMask: "255.0.0.0"),
            NEIPv4Route(destinationAddress: "127.0.0.0", subnetMask: "255.0.0.0"),
        ]
        settings.ipv4Settings = ipv4

        let dns = NEDNSSettings(servers: [dnsAddress])
        settings.dnsSettings = dns

        let proxy = NEProxySettings()
        proxy.httpServer = NEProxyServer(address: "127.0.0.1", port: Int(proxyPort))
        proxy.httpsServer = NEProxyServer(address: "127.0.0.1", port: Int(proxyPort))
        proxy.autoProxyConfigurationEnabled = false
        proxy.httpEnabled = true
        proxy.httpsEnabled = true
        proxy.exceptionList = [
            "10.0.0.0/8",
            "172.16.0.0/12",
            "192.168.0.0/16",
            "*.apple.com",
            "captive.apple.com",
        ]
        settings.proxySettings = proxy

        try await setTunnelNetworkSettings(settings)

        isRunning = true
        packetQueue = DispatchQueue(label: "com.aydocs.docspi.packet")

        readPackets()

        NSLog("[DocsPI VPN] Tunnel started successfully")
    }

    override func stopTunnel(with reason: NEProviderStopReason) async {
        NSLog("[DocsPI VPN] Stopping tunnel: \(reason)")
        isRunning = false
        try? await setTunnelNetworkSettings(nil)
        cancelTunnelWithError(nil)
    }

    override func handleAppMessage(_ messageData: Data) async -> Data? {
        let message = String(data: messageData, encoding: .utf8) ?? ""
        switch message {
        case "status":
            let status = "running=\(isRunning)"
            return status.data(using: .utf8)
        case "stop":
            await stopTunnel(with: .userInitiated)
            return "stopped".data(using: .utf8)
        default:
            return "ok".data(using: .utf8)
        }
    }

    private func readPackets() {
        packetQueue?.async { [weak self] in
            guard let self = self else { return }
            while self.isRunning {
                self.packetFlow.readPackets { [weak self] packets, protocols in
                    guard let self = self else { return }
                    if !packets.isEmpty {
                        self.packetFlow.writePackets(packets, withProtocols: protocols)
                    }
                }
            }
        }
    }
}

// MARK: - C FFI bridge for Rust

@_cdecl("docspi_start_vpn")
func docspi_start_vpn(dns: UnsafePointer<CChar>?, proxyPort: UInt16, mode: UnsafePointer<CChar>?) -> Bool {
    guard let provider = PacketTunnelProvider.runningInstance else { return false }
    // NEPacketTunnelProvider is started by the system, not directly from here
    // This C bridge is a registration point; actual start is via NETunnelProviderManager
    return true
}

@_cdecl("docspi_stop_vpn")
func docspi_stop_vpn() {
    // Stop is managed by system via stopTunnel(with:)
    // This is called from Rust to request stop through the app message mechanism
}

@_cdecl("docspi_vpn_running")
func docspi_vpn_running() -> Bool {
    return PacketTunnelProvider.runningInstance?.isRunning ?? false
}

extension PacketTunnelProvider {
    static weak var runningInstance: PacketTunnelProvider?
}
