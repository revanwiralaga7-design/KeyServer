# Key Server Manager

Aplikasi Android modern (Material 3, MVVM, Kotlin) + Backend REST API (Node.js + Express + PostgreSQL + JWT) untuk mengelola lisensi/key aplikasi/game.

## Struktur
- `android/` — Aplikasi Android (Material 3, Retrofit, Room, EncryptedSharedPreferences)
- `backend/` — REST API (Express, PostgreSQL, JWT Auth, Rate Limiter)

## Fitur Utama
- Login OAuth / PAT dengan token terenkripsi
- Dashboard statistik key
- Generate, edit, revoke, bulk generate key
- Filter dan search key
- Export CSV / JSON
- Notifikasi dan pengaturan
- Dark / Light mode

## Teknologi
- Kotlin 1.9, Material 3, MVVM, Retrofit, Room, Coroutines
- Node.js, Express, PostgreSQL, JWT, Winston, Helmet

## Setup Backend
```bash
cd backend
npm install
cp .env.example .env
# Edit .env dengan kredensial PostgreSQL kamu
npm run dev
```

## Setup Android
- Buka `android/` di Android Studio
- Sync Gradle
- Build `assembleRelease`

## Dokumentasi API
- `/api/auth/login`
- `/api/auth/refresh`
- `/api/keys`
- `/api/dashboard`

## Catatan Keamanan
- Token akses pengguna disimpan dengan EncryptedSharedPreferences.
- Backend menggunakan JWT Bearer Token.
- Untuk produksi, pastikan `.env` tidak dipublikasikan dan Client Secret dihapus/diregenerate jika diperlukan.
