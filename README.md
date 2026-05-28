<p align="center">
  <img src="public/docspi-logo.png" width="128" height="128" alt="DocsPI Logo" style="border-radius: 24px; box-shadow: 0 10px 30px rgba(0,0,0,0.5);">
</p>

<h1 align="center">DocsPI v5.0</h1>

<p align="center">
  <b>Discord ve internet erişim engellerini aşmak için tasarlanmış; çökmeye karşı dayanıklı, modern ve çok yönlü Yerel Proxy & DPI Bypass aracı.</b>
</p>

<p align="center">
  <a href="https://github.com/aydocs/DocsPI/releases">
    <img src="https://img.shields.io/github/v/release/aydocs/DocsPI?style=for-the-badge&color=3b82f6" alt="Release">
  </a>
  <a href="https://github.com/aydocs/DocsPI/stargazers">
    <img src="https://img.shields.io/github/stars/aydocs/DocsPI?style=for-the-badge&color=f59e0b" alt="Stars">
  </a>
  <a href="https://discord.gg/aydocs">
    <img src="https://img.shields.io/badge/Discord-7289DA?style=for-the-badge&logo=discord&logoColor=white" alt="Discord">
  </a>
  <img src="https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge" alt="License">
</p>

---

## 📸 Ekran Görüntüleri

<div align="center">
  <img src="images/1.png" alt="DocsPI Ekran Görüntüsü 1" width="35%">
  <img src="images/2.png" alt="DocsPI Ekran Görüntüsü 2" width="35%">
</div>

---

## 📋 İçindekiler

- [Neden DocsPI?](#-neden-docspi)
- [v5.0.0 Yenilikleri](#-v500-yenilikleri)
- [Özellikler](#-özellikler)
- [3 Kademeli DPI Bypass Motoru](#-3-kademeli-dpi-bypass-motoru)
- [Güvenlik](#-güvenlik)
- [Bağımsız Güvenlik İncelemesi](#-bağımsız-güvenlik-incelemesi)
- [Sistem Gereksinimleri](#-sistem-gereksinimleri)
- [Kurulum](#-kurulum)
- [Yapımcı ve Geliştirici](#-yapımcı-ve-geliştirici)
- [Gizlilik](#-gizlilik)

---

## 💡 Neden DocsPI?

Piyasadaki diğer CMD/Java tabanlı araçların (GoodbyeDPI, GreenTunnel vb.) en büyük sorunu; **BSOD veya ani kapanma durumlarında sistem proxy ayarlarını havada bırakarak interneti kesmesidir.**

DocsPI, baştan aşağı **Rust (Tauri v2)** altyapısıyla kodlanmış olup **Sentinel Recovery**, **Zombi Process Temizleme** ve **Kurumsal Proxy Yedekleme** (Backup & Restore) sistemleriyle donatılmıştır. Bağlantı ne şekilde koparsa kopsun, internet ayarlarınız otomatik olarak eski haline döner.

---

## ✨ v5.0.0 Yenilikleri

DocsPI v5, baştan aşağı yenilenmiş bir mimari ve dünya standartlarında özelliklerle geliyor:

*   **🌍 13 Dil Desteği** — Türkçe, İngilizce, Çince, Hintçe, İspanyolca, Fransızca, Arapça (RTL), Portekizce, Rusça, Japonca, Almanca, İtalyanca ve Korece.
*   **🎨 Dinamik Tema Sistemi** — Koyu (Dark) ve Açık (Light) mod desteği ile tamamen özelleştirilebilir modern UI.
*   **🛡️ Gelişmiş Bypass (Fake SNI)** — Ağır DPI sistemlerini aşmak için sahte paket (Fake Packet) ve SNI gizleme desteği.
*   **🎮 Oyun Modu (Roblox & UDP Fix)** — Roblox, Discord ve UDP tabanlı tüm oyunlar için özel kernel bypass motoru.
*   **🚀 Auto-Escalation** — Bağlantı kurulamadığında otomatik olarak daha güçlü bypass modlarına (Turbo -> Dengeli -> Güçlü) geçiş.
*   **📈 Bağlantı İstatistikleri** — Detaylı oturum geçmişi, uptime takibi ve gerçek zamanlı ping ölçümü.
*   **⚡ Sentinel Recovery 2.0** — Çökmelere karşı çok daha agresif zombi süreç temizleme ve otomatik onarım.
*   **🔍 Bypass Doğrulama Testi** — Bağlantının gerçekten çalışıp çalışmadığını arayüz üzerinden anlık test etme.

---

## ✨ Genel Özellikler

- **Sistem Geneli Akıllı Proxy** — Windows proxy ayarlarını otomatik yönetir; Discord, Roblox, tarayıcılar ve diğer tüm uygulamalar engeli aşar.

<table>
  <tr>
    <td width="35%" align="center">
      <img src="images/2.png" alt="LAN Paylaşımı" width="70%">
    </td>
    <td width="55%" valign="middle">
      <b>📱 LAN Paylaşımı — Tüm Ev/Ağa Dağıtım</b><br><br>
      Dahili PAC sunucusu sayesinde aynı ağdaki telefon, tablet veya konsol cihazlarınızı engelsiz ağa bağlayabilirsiniz.<br><br>
      <i>Wi-Fi Ayarları → Proxy → Otomatik URL kısmına QR kodu okutarak anında bağlanın.</i>
    </td>
  </tr>
</table>

- **DoH (DNS over HTTPS)** — ISP'lerin Port 53 DNS sorgularını izlemesini önlemek için Cloudflare, Google, AdGuard, Quad9 ve OpenDNS üzerinden şifreli DNS.
- **Canlı Soft-Restart** — DNS veya ayar değişikliklerinde uygulamayı yeniden başlatmanıza gerek yoktur; bağlantı otomatik olarak yeni ayarlara geçiş yapar.
- **Modern Arayüz** — Windows 11 uyumlu React/Vite arayüzü, canlı log monitörü ve 13 dil desteği.
- **Sistem Tepsisi (Tray)** — Arka planda sessiz çalışma ve sağ tık ile hızlı mod değiştirme.

---

## ⚙️ 3 Kademeli DPI Bypass Motoru

| Mod | İsim | Açıklama |
| :---: | :--- | :--- |
| **0** | **Turbo** | En düşük gecikme. Hafif filtreleri (SNI bazlı) anında aşar. |
| **1** | **Dengeli** | Hızlı ve stabil. TLS paketlerini Chunk Split yöntemiyle böler. |
| **2** | **Güçlü** | En zorlu DPI'lar için Sahte Paket (Fake Packet) + SNI Gizleme. |

> Gelişmiş ayarlardan 1 / 2 / 4 / 8 / 16 baytlık Chunk Size ince ayarı yapılabilir.

---

## 🛡️ Güvenlik

1. **Sentinel Recovery** — Ani kapanma/BSOD durumunda uygulama, bir sonraki açılışta proxy kirliliğini tespit ederek otomatik temizler.
2. **Proxy Backup & Restore** — Kurumsal/şirket proxy ayarları varsa, DocsPI bunları kapatılırken otomatik geri yükler.
3. **Rust Native WinAPI** — Registry ve yönetici işlemleri, CMD/PowerShell yerine Rust'ın native WinAPI entegrasyonuyla yürütülür.
4. **Thread-Rate Limitli PAC Sunucusu** — Aynı ağdaki yabancı cihazların PAC portuna aşırı bağlantı açmasını önleyen asenkron bağlantı limiti.
5. **Strict CSP + Tauri İzolasyonu** — Arayüzden gelebilecek zararlı kod ihtimalleri sıkı CSP politikası ve Tauri'nin izole shell yetkisiyle engellenir.

---

## 🔍 Bağımsız Güvenlik İncelemesi

> Geliştirme süreci tamamlandıktan sonra proje, **Claude Code (Anthropic)** tarafından bağımsız bir güvenlik incelemesine tabi tutulmuştur.

- **Tauri v2 İzolasyonu** — WebView ile Rust backend arasındaki her komut beyaz listeyle kısıtlanmıştır.
- **Strict CSP** — Dış bağlantılar yalnızca güvenli kaynaklarla sınırlandırılmış, XSS riskleri minimize edilmiştir.
- **DOMPurify** — Log çıktıları arayüze aktarılmadan önce sanitize edilmektedir.
- **PAC Sunucusu Rate Limiting** — Yerel ağ kaynaklı DoS girişimlerine karşı korumalıdır (`MAX_PAC_CONNECTIONS = 50`).
- **Native WinAPI Registry Erişimi** — Proxy ayarları PowerShell yerine doğrudan WinAPI üzerinden yönetilir.
- **Sıfır Telemetri** — Kaynak kodda dışarıya veri gönderen hiçbir yapı tespit edilmemiştir.

---

## 💻 Sistem Gereksinimleri

| Gereksinim | Detay |
| :--- | :--- |
| İşletim Sistemi | Windows 10 / Windows 11 |
| Mimari | x64 |
| RAM | ~60 MB (WebView2 dahil) |
| Yetki | Yönetici (Administrator) |

---

## 🔧 Kurulum

1.  **İndirin** — [Releases sayfasından](https://github.com/aydocs/DocsPI/releases) en güncel sürümü indirin.
2.  **Başlatın** — Uygulamayı **yönetici olarak** çalıştırın (Kernel sürücüsü ve proxy ayarları için gereklidir).
3.  **Bağlanın** — İstediğiniz modu seçin ve **BAĞLAN** butonuna basın.

---

## 👨‍💻 Yapımcı ve Geliştirici
Bu proje [**aydocs**](https://github.com/aydocs) tarafından v5 sürümü ile tamamen modernize edilmiş ve geliştirilmiştir.

### 📜 Krediler ve Teşekkür
*   [**aydocs**](https://github.com/aydocs) - Ana Yapımcı, v5 Geliştiricisi ve Tasarımcı.
*   [**shencim**](https://github.com/shencim) - Projenin ilk kodlarını yazan ve temellerini atan orijinal geliştirici.

---

## 🔒 Gizlilik

> [!IMPORTANT]
> DocsPI **hiçbir telemetri veya veri toplama yapısı barındırmaz.**
> IP adresiniz, ziyaret ettiğiniz siteler ve sistem bilgileriniz hiçbir sunucuya gönderilmez. Uygulama logları yalnızca RAM'de tutulur ve program kapandığında silinir.

---

## ⚖️ Sorumluluk Reddi

- DocsPI yalnızca yerel makinenizde HTTPS trafiğinin TLS paketlerini (SNI katmanı) böler (Packet Fragmentation). Uzak bir VPN sunucusuyla veri alışverişi yapmaz.
- Yazılım kişisel kullanıma açık ve ücretsizdir. Kullanımdan doğacak tüm teknik ve yasal sorumluluk kullanıcıya aittir.

<br>
<div align="center">
  <strong>DocsPI ile kesintisiz ve özgür internete hoş geldiniz.</strong>
</div>
