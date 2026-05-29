use serde::{Deserialize, Serialize};

#[derive(Debug, Clone, Serialize, Deserialize)]
pub struct VpnConfig {
    pub dns: String,
    pub proxy_port: u16,
    pub bypass_mode: String,
}

#[derive(Debug, Clone, Serialize)]
pub struct VpnStatus {
    pub running: bool,
    pub bytes_rx: u64,
    pub bytes_tx: u64,
}

// ---------------------------------------------------------------------------
// Android — bridges to DocsPiVpnService via Tauri JNI
// ---------------------------------------------------------------------------

#[cfg(target_os = "android")]
mod android_bridge {
    use super::*;
    use std::sync::atomic::{AtomicBool, Ordering};

    static VPN_RUNNING: AtomicBool = AtomicBool::new(false);

    pub fn start_vpn_impl(config: VpnConfig) -> Result<(), String> {
        // In production, this calls DocsPiVpnService.startVpn() via
        // tauri::android::current_activity() JNI bridge.
        // Currently scaffolded for the native build pipeline.
        let _ = config;
        VPN_RUNNING.store(true, Ordering::Relaxed);
        Ok(())
    }

    pub fn stop_vpn_impl() -> Result<(), String> {
        VPN_RUNNING.store(false, Ordering::Relaxed);
        Ok(())
    }

    pub fn vpn_status_impl() -> VpnStatus {
        VpnStatus {
            running: VPN_RUNNING.load(Ordering::Relaxed),
            bytes_rx: 0,
            bytes_tx: 0,
        }
    }
}

#[cfg(target_os = "android")]
pub use android_bridge::*;

// ---------------------------------------------------------------------------
// iOS — bridges to PacketTunnelProvider via C FFI
// ---------------------------------------------------------------------------

#[cfg(target_os = "ios")]
mod ios_bridge {
    use super::*;
    use std::sync::atomic::{AtomicBool, Ordering};

    static VPN_RUNNING: AtomicBool = AtomicBool::new(false);

    extern "C" {
        fn docspi_start_vpn(
            dns: *const std::os::raw::c_char,
            proxy_port: u16,
            mode: *const std::os::raw::c_char,
        ) -> bool;
        fn docspi_stop_vpn();
        fn docspi_vpn_running() -> bool;
    }

    pub fn start_vpn_impl(config: VpnConfig) -> Result<(), String> {
        let dns_c =
            std::ffi::CString::new(config.dns).map_err(|e| format!("CString: {}", e))?;
        let mode_c =
            std::ffi::CString::new(config.bypass_mode).map_err(|e| format!("CString: {}", e))?;
        unsafe {
            if docspi_start_vpn(dns_c.as_ptr(), config.proxy_port, mode_c.as_ptr()) {
                VPN_RUNNING.store(true, Ordering::Relaxed);
                Ok(())
            } else {
                Err("VPN start returned false".to_string())
            }
        }
    }

    pub fn stop_vpn_impl() -> Result<(), String> {
        unsafe { docspi_stop_vpn() }
        VPN_RUNNING.store(false, Ordering::Relaxed);
        Ok(())
    }

    pub fn vpn_status_impl() -> VpnStatus {
        let running = unsafe { docspi_vpn_running() };
        VpnStatus {
            running: running || VPN_RUNNING.load(Ordering::Relaxed),
            bytes_rx: 0,
            bytes_tx: 0,
        }
    }
}

#[cfg(target_os = "ios")]
pub use ios_bridge::*;

// ---------------------------------------------------------------------------
// Desktop stubs
// ---------------------------------------------------------------------------

#[cfg(not(any(target_os = "android", target_os = "ios")))]
mod desktop_stub {
    use super::*;

    pub fn start_vpn_impl(_config: VpnConfig) -> Result<(), String> {
        Err("VPN is only available on Android/iOS builds".to_string())
    }
    pub fn stop_vpn_impl() -> Result<(), String> {
        Ok(())
    }
    pub fn vpn_status_impl() -> VpnStatus {
        VpnStatus { running: false, bytes_rx: 0, bytes_tx: 0 }
    }
}

#[cfg(not(any(target_os = "android", target_os = "ios")))]
pub use desktop_stub::*;
