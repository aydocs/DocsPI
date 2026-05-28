# 🧠 DocsPI — Project Genesis Plan
> "Hasiktir lan, bunu yapan dahi."
> Kullanıcı hiçbir şey düşünmez. Tıklar, çalışır. Free SaaS gibi.

---

## Vizyon

**DocsPI** bir DPI bypass aracı olmanın **ötesine geçecek**. İnternet özgürlüğünün **akıllı katmanı** olacak. Kullanıcı ne DPI ne proxy ne DNS ne bypass bilmeyecek. Sadece "internetim kısıtlı" deyip uygulamayı açacak — gerisini DocsPI halledecek.

Her platformda çalışacak. Hiçbir şey kurmayı gerektirmeyecek. Ücretsiz olacak ama "acaba premiuma geçsem mi" hissi verecek kadar kaliteli olacak.

---

## Felsefe: Zero-Friction Law

| Kural | Açıklama |
|---|---|
| **#1** | Kullanıcı bir kez tıklar, iş hallolur. |
| **#2** | Hiçbir ayar varsayılan olarak değiştirilmek zorunda değildir. |
| **#3** | Uygulama, kullanıcının ne istediğini yapmadan önce bilir. |
| **#4** | Her hata için çözüm otomatiktir. |
| **#5** | "Gelişmiş ayarlar" ekranda görünmez ama ihtiyaç olunca bulunur. |

---

## Mimarî Katmanlar

```
┌─────────────────────────────────────────────────────┐
│                   UI KATMANI                         │
│  Tauri v2 (Rust) + React + Vite + Framer Motion     │
│  Universal (Windows/macOS/Linux/Android/iOS)        │
├─────────────────────────────────────────────────────┤
│                 ORKESTRASYON KATMANI                  │
│  Rust async engine — event-driven, actor model       │
│  State machine: IDLE → DETECTING → BYPASSING → OK   │
├─────────────────────────────────────────────────────┤
│              ZEKÂ KATMANI (AI/ML)                    │
│  DPI fingerprinting → bypass strategy optimizer      │
│  Anomaly detection → auto fallback                   │
├─────────────────────────────────────────────────────┤
│             BYPASS MOTOR KATMANI                      │
│  Layer 1: Fake SNI + Chunk Split                     │
│  Layer 2: WinDivert / raw socket                     │
│  Layer 3: Tunnel (WireGuard-like embedded)           │
│  Layer 4: Mesh Relay (community P2P)                 │
├─────────────────────────────────────────────────────┤
│             SİSTEM ENTEGRASYON KATMANI                │
│  Proxy yönetimi, DNS, routing, firewall, TUN/TAP     │
├─────────────────────────────────────────────────────┤
│             TELEMETRİ / ANALİTİK (OPT-IN)            │
│  Anonim başarı oranı → global bypass heatmap          │
└─────────────────────────────────────────────────────┘
```

---

## Phase 1 — Temel (MEVCUT DURUM)
✅ Rename tamamlandı

---

## Phase 2 — Windows Sağlamlaştırma (1-2 hafta)

### 2.1 Akıllı Auto-Detection Engine
- Kullanıcının ISP'sini otomatik tespit et (API veya ping analizi)
- ISP + bölge bazlı bypass profili (community-driven)
- İlk açılışta hiçbir şey sormadan en iyi modu SEÇ ve BAĞLAN

### 2.2 Premium DNS Sistemi
- DoH/DoT otomatik seçim (en hızlı DNS'i test et, kullan)
- DNS-over-Proxy (DNS sorgularını bypass üzerinden yönlendir)
- Türkiye için özel: 1.1.1.1 + 9.9.9.9 + 8.8.8.8 fallback zinciri
- DNS benchmark: başlangıçta 5 sn'de en hızlı DNS'i bul

### 2.3 Game Mode 2.0
- UDP paketleri için raw socket bypass
- Discord ses kalitesi optimizasyonu (ayrı calisma)
- Oyun tanıma: popüler oyunlar için özel rule set
- Gecikme (latency) grafiği: bypass öncesi/sonrası karşılaştırma

### 2.4 Auth Sistemi (FREE — KESİNLİKLE ÜCRETSİZ)
- Cihaz ID'si bazlı basit aktivasyon
- "Free Tier" = her şey açık, sadece kullanım istatistiği
- Amaç: Kötü amaçlı kullanımı engellemek değil, kullanıcıya "özel" hissettirmek

### 2.5 Eksik Diller (10 dil daha)
- Çeviri tamamlama [0/10 → 10/10]
- Otomatik dil algılama (OS dili neyse o)

---

## Phase 3 — Çapraz Platform (3-4 hafta)

### 3.1 macOS Desteği
- `NetworkExtension` framework ile NEPacketTunnelProvider
- System proxy ayarları (SCDynamicStore)
- Apple Silicon + Intel universal binary
- Notarization hazırlığı

### 3.2 Linux Desteği
- `iptables` / `nftables` + `tun2socks` tabanlı bypass
- GNOME/KDE/Wayland sistem tepsisi
- AppImage + Flatpak + Snap
- NetworkManager proxy entegrasyonu

### 3.3 Android (Tauri Mobile)
- VpnService API ile local VPN bypass
- Arka planda çalışma (Foreground Service)
- Android 13+ entegrasyonu

### 3.4 iOS (Tauri Mobile — Sınırlı)
- NEPacketTunnelProvider (şartlı)
- TestFlight dağıtımı
- Enterprise sertifika ile sideload

---

## Phase 4 — Zekâ Katmanı (AI/ML) (4-6 hafta)
🚀 **Burası projeyi "dahi" seviyesine çıkaracak bölüm.**

### 4.1 DPI Fingerprinting Engine
- Çalışma şekli: Belli bir siteye istek at → Gelen DPI hatasını analiz et (RST, HTTP redirect, throttling, SNI filtresi, IP blackhole)
- DPI tipini tanı: `DPI_TURKTELEKOM_V1`, `DPI_VODAFONE_V2`, `DPI_IRAN`, `DPI_CHINA_GREATFIREWALL`, vs.
- Her DPI tipi için optimize edilmiş bypass stratejisi
- Başarısız olursa 1 sn içinde sonraki stratejiye geç (auto-escalation)

### 4.2 Bypass Strategy Optimizer
- Reinforcement learning: Hangi bypass stratejisi hangi ISP'de işe yarıyor?
- Community-driven: Başarılı yapılandırmalar anonim olarak paylaşılır
- Her güncellemede global model güncellenir
- Zamanla kendi kendini optimize eden bypass

### 4.3 Anomaly Detection
- Bağlantı kalitesini sürekli izle (packet loss, latency, throughput)
- "Sessiz throttling" tespiti: İndirme hızı aniden düşerse alternatif bypass dene
- ISP'nin yeni DPI güncellemesini otomatik tespit et

### 4.4 Predictive Bypass
- Kullanıcı hangi siteye girecek? (Sık kullanılanlardan tahmin)
- Önceden bypass hazırlığı yap (pre-connect)
- "0 sn bekleme" hissi

---

## Phase 5 — Mesh Relay Network (Peer-to-Peer) (4-6 hafta)
🔥 **DocsPI'ı bir "platform" yapacak özellik.**

### 5.1 Nasıl çalışır
- DocsPI açık olan cihazlar birbirini bulur (DHT tabanlı)
- Kullanıcının ISP'si çok katı DPI uyguluyorsa → trafik başka bir DocsPI kullanıcısı üzerinden yönlendirilir
- Tamamen şifreli (WireGuard tunnel)
- Relay node olmak opsiyonel ve varsayılan olarak KAPALI

### 5.2 Use Case
- İran'da bir kullanıcı → Türkiye'deki relay → Hedef site
- SSCB'de bir kullanıcı → AB'deki relay → Hedef site
- Gecikme artışı minimum (%10-15)

### 5.3 Güvenlik
- Relay node KULLANICININ TRAFİĞİNİ GÖREMEZ (uçtan uca şifreli)
- Relay node olarak çalışmak için ayrıca izin istenir
- Hiçbir log tutulmaz

---

## Phase 6 — SaaS Experience (UI/UX) (2-3 hafta)
💎 **Kullanıcıya "acaba premium mu alsam" dedirtecek detaylar (ama hepsi ücretsiz).**

### 6.1 First Launch Experience
```
1. Uygulama açılır → splash ekranı (1 sn)
2. "İnternetinizi test ediyoruz..." → auto-DPI detection (2 sn)
3. "Optimize ediliyor..." → bypass başlatılır (1 sn)
4. ✅ Bağlandınız! → Ana ekran
Toplam: 4 saniye. Kullanıcı hiçbir şeye tıklamadı.
```

### 6.2 Live Dashboard
- Gerçek zamanlı trafik grafiği (bypass edilen/edilmeyen)
- Anlık ping, packet loss, Jitter
- Bugüne kadar kaç MB bypass edildi
- Aktif oturum süresi
- "Bypass kalitesi" skoru: A+ / A / B / C

### 6.3 Speed Test Built-in
- "Test et" butonu → bypass kapalıyken hız testi → bypass açıkken hız testi
- Karşılaştırmalı sonuç: "DocsPI olmadan 5 Mbps, DocsPI ile 85 Mbps"
- Grafik gösterim

### 6.4 ISP Durum Sayfası
- "Türk Telekom İstanbul — 3 farklı DPI varyantı tespit edildi"
- "Son 24 saatte 1.234 başarılı bypass"
- "Şu an her şey çalışıyor ✅"
- Community raporları: "Vodafone Ankara'da bazı kullanıcılar sorun yaşıyor ⚠️"

### 6.5 Smart Notifications
- "Discord'da ses kesilmesi mi yaşıyorsunuz? Game Mode'u açalım mı?"
- "YouTube yavaşladı, alternatif bypass stratejisine geçtik"
- "Wi-Fi değişti (+farklı ISP tespit edildi), ayarları güncelliyoruz"

### 6.6 Global Bypass Heatmap
- Dünya haritası: Hangi ülkede hangi DPI aktif
- DocsPI kullanıcılarının başarı oranı
- "Bu bölgede yeni bir DPI tespit edildi" bildirimi

---

## Phase 7 — Enterprise & Community (sürekli)

### 7.1 Plugin Sistemi
- WASM tabanlı plugin sistemi
- Community yeni bypass modülleri yazabilir
- Plugin store (docsPI içinden)

### 7.2 API
- REST API ile bypass durumu sorgulama
- CLI tool (`docspi status`, `docspi connect`, `docspi game`)
- Automator/Shortcuts entegrasyonu (macOS)

### 7.3 Auto-Update Infrastructure
- GitHub Releases üzerinden delta update
- Signature verification
- Rollback desteği
- Güncelleme zorunlu değil, arka planda indir

### 7.4 CI/CD Pipeline Mükemmelliği
- Tüm platformlar için parallel build (GitHub Actions + self-hosted)
- Cross-compile Go bypass engines
- Automated testing (headless browser ile UI testi)
- Automated DPI test (gerçek ortamda bypass doğrulama)
- Release automation: version bump → tag → build → release notes → publish

---

## Phase 8 — Güvenlik & Şeffaflık

### 8.1 Open Source Everything
- Tüm kaynak kod halka açık
- Build reproducible (aynı commit → aynı binary)
- SBOM (Software Bill of Materials) her release'de

### 8.2 Security Audit
- 3. parti bağımsız güvenlik denetimi (her major release)
- Bug bounty programı (yönetilebilir seviyede)
- Audit raporları public

### 8.3 Privacy Promise
- Zero telemetry default
- Telemetry açılırsa: anonim, toplu, opt-in
- Network graph: HİÇBİR VERİ toplanmaz

---

## Teknik Borç & Altyapı İyileştirmeleri

### Hemen Yapılacaklar
- [ ] Rust'taki `#[cfg(target_os = "windows")]` bağımlılığını abstract layer ile kaldır
- [ ] OS-specific modüllere ayır: `platform/windows/`, `platform/macos/`, `platform/linux/`
- [ ] Event-driven mimari: `enum EngineEvent { Connected, Disconnected, DPIChanged, ... }`
- [ ] Error handling: `anyhow` → custom error types
- [ ] State machine: Mevcut spaghetti durum yönetimini formal FSM'e çevir
- [ ] Unit test: Rust tarafında kritik fonksiyonlar için test ekle
- [ ] Integration test: Headless browser ile UI test

### Go Divert Motoru
- [ ] `fake_hello.go`'ya `--fake-sni` parametresi ekle
- [ ] Go modülünü public repo'ya hazırla
- [ ] Windows dışı platformlar için raw socket alternatifi

---

## Zaman Çizelgesi (Tahmini)

| Phase | Süre | Çıktı |
|---|---|---|
| Phase 1 — Rename | ✅ TAMAM | DarknesDPI → DocsPI |
| Phase 2 — Windows | 1-2 hafta | Sağlam, hatasız Windows sürümü |
| Phase 3 — Cross-platform | 3-4 hafta | macOS + Linux + Android |
| Phase 4 — AI/ML | 4-6 hafta | Kendi kendine öğrenen bypass |
| Phase 5 — Mesh | 4-6 hafta | P2P relay ağı |
| Phase 6 — UX | 2-3 hafta | SaaS kalitesinde kullanıcı deneyimi |
| Phase 7 — Community | Sürekli | Plugin sistemi, API, CI/CD |
| Phase 8 — Security | Sürekli | Güvenlik + şeffaflık |

**Toplam: ~6 ay** (full-time bir geliştirici için)

---

## "Dahi" Anları (Projenin Zirve Noktaları)

> **1. İlk açılışta kullanıcı hiçbir şey yapmadan bypass başlar.**
> Kullanıcı: "E... bu çalıştı mı yani? Hiçbir şey yapmadım?"
> → İşte bu dahi anı.

> **2. AI, kullanıcının ISP'sindeki DPI'ı otomatik tanır ve en iyi stratejiyi seçer.**
> "Türk Telekom İstanbul, yeni DPI varyantı tespit edildi.
> Chunk Split (4 bayt) + Fake SNI ile bypass başlatıldı."

> **3. Mesh relay: Hiçbir şekilde bypass olmuyorsa, başka bir DocsPI kullanıcısı üzerinden bağlanır.**
> "Direkt bypass başarısız. Community relay üzerinden yönlendiriliyor..."
> Kullanıcı: "Ne... nasıl? Birisi mi yardım ediyor?"

> **4. Tüm platformlarda AYNI uygulama, AYNI deneyim.**
> Windows'ta kapat → Telefonda devam et → Linux'ta devam et.
> Hesap yok, login yok. Sadece çalışır.

> **5. Built-in speed test: "DocsPI kapalı → 3 Mbps, DocsPI açık → 92 Mbps"**
> Kullanıcı: "Abi bu şey resmen internetimi 30x hızlandırdı."

---

## Ölçüm Metrikleri (Proje Başarısını Nasıl Ölçeriz?)

| Metrik | Hedef |
|---|---|
| İlk bağlantı süresi | < 5 saniye (AI detection + bypass) |
| Başarı oranı (tüm ISP'ler) | > %98 |
| Kullanıcı memnuniyeti | > 4.5/5 |
| Çapraz platform desteği | 5/5 (Win/Mac/Linux/Android/iOS) |
| Community relay node sayısı | > 10.000 |
| Hata bildirimi → fix süresi | < 24 saat |
| "Hiçbir şey anlamadım ama çalışıyor" oranı | > %90 |

---

## Son Söz

Bu proje **Türkiye'den çıkmış en büyük açık kaynak DPI bypass projesi** olacak. Dünyada GoodbyeDPI, ByeDPI, SpoofDPI, Zapret, GreenTunnel gibi araçların hepsi ya çok teknik, ya platforma özel, ya da bakımı zor.

**DocsPI, hepsini tek çatı altında toplayacak ve bir "product" haline getirecek.**

> "Bunu gören 'oha' diyecek, kullanan 'dahi yapmış bunu' diyecek, anlamayan 'nasıl yani çalışıyor' diyecek."

---

*Plan oluşturulma: 28 Mayıs 2026*
*Yazar: Sisyphus (OhMyOpenCode AI)*
