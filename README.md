<h1 align="center">Moonfin DV — Dolby Vision Fork</h1>
<h3 align="center">Personal fork of Moonfin for Android TV with Dolby Vision Profile 7 compatibility</h3>

---

<p align="center">
   <img width="4305" height="2659" alt="splash-background" src="https://github.com/user-attachments/assets/c05882da-81ce-47e9-a4b2-c995c337b9b9" />
</p>

[![License](https://img.shields.io/github/license/skipydg/Moonfin-DV-AndroidTV-FireTV.svg)](https://github.com/skipydg/Moonfin-DV-AndroidTV-FireTV)
[![Release](https://img.shields.io/github/release/skipydg/Moonfin-DV-AndroidTV-FireTV.svg)](https://github.com/skipydg/Moonfin-DV-AndroidTV-FireTV/releases)
[![github](https://img.shields.io/github/downloads/skipydg/Moonfin-DV-AndroidTV-FireTV/total?logo=github&label=Downloads)](https://github.com/skipydg/Moonfin-DV-AndroidTV-FireTV/releases)

> **⬆️ Upstream:** [Moonfin-Client/AndroidTV-FireTV](https://github.com/Moonfin-Client/AndroidTV-FireTV) — this fork tracks upstream and rebases on top of it regularly.

---

## What This Fork Adds

This is a personal fork of [Moonfin for Android TV](https://github.com/Moonfin-Client/AndroidTV-FireTV) that adds **Dolby Vision Profile 7 compatibility** on top of everything upstream Moonfin provides.

### 🎬 Dolby Vision Profile 7 Compatibility Mode

Most Android TV hardware (including Nvidia Shield, Fire TV Stick 4K, and Chromecast with Google TV) supports **Dolby Vision Profile 8** but not **Profile 7** (the dual-layer format used in UHD Blu-ray rips and MakeMKV MKV files). Without this fix, those files either fail to play or fall back to HDR10/SDR.

This fork patches the ExoPlayer renderer to transparently **rewrite DV Profile 7 streams as Profile 8.1** before they reach MediaCodec — enabling native DV playback without transcoding.

**Settings → Playback → Advanced → Video:**

| Setting | Description |
|---------|-------------|
| **Dolby Vision Compatibility** | Rewrites DV Profile 7 sources as Profile 8.1. Enable for UHD Blu-ray rips and MakeMKV MKV files. |
| **Force Compatibility Mode** | Applies the Profile 8.1 rewrite even on devices that report DV Profile 7 support. Use if DV playback is unstable. |

**What works:**
- UHD Blu-ray rips (DVHE.DTB dual-layer)
- MakeMKV MKV files with DV metadata
- Standard DV P7 direct-play from both Jellyfin and Emby

---

## Installation

Download the latest APK from the [Releases page](https://github.com/skipydg/Moonfin-DV-AndroidTV-FireTV/releases).

**Supported Devices:**
- Android TV devices (Android 6.0+)
- Nvidia Shield TV
- Amazon Fire TV / Fire TV Stick 4K
- Google TV (Chromecast with Google TV)

### Sideloading
1. Enable **Unknown Sources** / **Install Unknown Apps** in your device settings
2. Download the APK directly to your device or transfer it via USB
3. Use a file manager to install

> **Note:** This is a debug-signed APK. If you have the Play Store version of Moonfin installed, you'll need to uninstall it first (different signing key). If you have the official GitHub release of upstream Moonfin installed, same applies.

---

## Building from Source

### Prerequisites
- JDK 21
- Android SDK (API 23+)
- Android Studio (optional but recommended)

### Steps

```bash
git clone https://github.com/skipydg/Moonfin-DV-AndroidTV-FireTV.git
cd Moonfin-DV-AndroidTV-FireTV
git checkout dv-compat

JAVA_HOME=/path/to/jdk21 ANDROID_HOME=/path/to/android-sdk \
  ./gradlew assembleGithubDebug
```

APK output: `app/build/outputs/apk/github/debug/`

### Workflow Scripts

| Script | What it does |
|--------|-------------|
| `release.sh` | Bumps version in `gradle.properties`, builds APK, tags, and publishes a GitHub release |
| `sync.sh` | Fetches latest from upstream Moonfin and rebases the DV compat commits on top |

---

## Relationship to Upstream Moonfin

This fork **does not diverge** from Moonfin's features — it only adds on top. The `dv-compat` branch is kept in sync with `upstream/main` via rebase, so all upstream improvements land here automatically.

If you don't need DV Profile 7 compat, use the official [Moonfin release](https://github.com/Moonfin-Client/AndroidTV-FireTV/releases) instead.

---

## Everything Else (from Upstream Moonfin)

All features below are inherited from [Moonfin-Client/AndroidTV-FireTV](https://github.com/Moonfin-Client/AndroidTV-FireTV) and maintained upstream. See their README for full details.

<details>
<summary><strong>Full upstream feature list</strong></summary>

### Supported Servers

| Server | Minimum Version | Status |
|--------|----------------|--------|
| Jellyfin | 10.8.0+ | Full support |
| Emby | 4.8.0.0+ | Full support |

### Emby Server Support
- Connect to Emby Server 4.8.0.0+ alongside Jellyfin
- Automatic server type detection during setup
- WebSocket real-time events with reconnection and jitter-based backoff
- In-memory caching for library views and display preferences
- Feature gating hides Jellyfin-only features (SyncPlay, Media Segments, Lyrics) when on Emby

### SyncPlay
- Synchronized group playback with dynamic speed adjustments
- Buffering and ready state reporting
- User notifications for group join/leave events

### Playlist System
- Create, manage, and share playlists
- Add to Playlist button on detail screens
- Public playlist support

### Jellyseerr & Seerr Integration
- Browse trending, popular, and recommended movies/shows
- Request content in HD or 4K directly from your TV
- Moonfin Proxy Mode — route requests through the Moonfin server plugin
- NSFW Content Filtering, smart season selection, per-user settings

### MDBList Ratings Integration
- AniList, IMDB, Letterboxd, Metacritic, MyAnimeList, Roger Ebert, Rotten Tomatoes, TMDB, Trakt

### Customizable Toolbar
- Toggle Shuffle, Genres, and Favorites buttons
- Library row toggle for a cleaner home screen

### Featured Media Bar
- Rotating showcase of movies and TV shows on the home screen
- Profile-aware refresh, ratings, genres, runtime overview

### Enhanced Navigation
- Left Sidebar Navigation with expandable icons/text
- Folder View, Genres as sortable tiles, dynamic library buttons

### In-App Trailer Previews
- Invidious-based trailer playback with SponsorBlock integration
- Episode preview overlays, DASH quality support

### Redesigned Libraries & Details (Compose)
- Modern Compose-based library grid and details screens
- Adaptive card sizing, infinite scroll, filter/sort dialogs

### Playback & Media Control
- ASS/SSA subtitle support with customizable font scaling
- Subtitle delay & positioning controls
- Unpause Rewind, Pre-Playback Track Selection
- Next Episode Countdown, Trickplay Scrub
- Automatic Screensaver Dimming

### UI Polish
- Customizable focus accent color
- Card Focus Expansion, adjustable backdrop blur
- Compose-based dialogs throughout

</details>

---

## Screenshots

<img width="1920" height="1080" alt="Screenshot_20260219_115555" src="https://github.com/user-attachments/assets/11d61a01-b9be-4a30-9c28-ea355f962dd2" />
<img width="1920" height="1080" alt="Screenshot_20260219_115456" src="https://github.com/user-attachments/assets/481665ea-0e22-42e4-9424-058b21f968c1" />
<img width="1920" height="1080" alt="Screenshot_20260219_120753" src="https://github.com/user-attachments/assets/2beceee0-2c15-4f7d-927b-5de079aa53b8" />
<img width="1920" height="1080" alt="Screenshot_20260219_115429" src="https://github.com/user-attachments/assets/bff08ce4-a31e-4859-87aa-6246d9494460" />

---

## Credits

- **[Moonfin-Client](https://github.com/Moonfin-Client)** — upstream project this fork is based on
- **[Jellyfin Project](https://jellyfin.org)** — the foundation Moonfin itself builds on
- **Jellyfin Android TV Contributors** — all the developers who built the original client

## License

GPL v2, inherited from the upstream Jellyfin Android TV project. See [LICENSE](LICENSE).

---

<p align="center">
   Personal fork by <a href="https://github.com/skipydg">skipydg</a> · not affiliated with Moonfin-Client or the Jellyfin project<br>
   <a href="https://github.com/Moonfin-Client/AndroidTV-FireTV">⬆️ Upstream Moonfin</a>
</p>
