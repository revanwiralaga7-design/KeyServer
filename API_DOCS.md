# Key Server Manager — API Documentation

Base URL: `https://your-server.com/api`

## Authentication
Semua endpoint memerlukan header:
```
Authorization: Bearer <token>
```

## Endpoints

### Auth
- `POST /auth/login` — Login (username, password) → JWT
- `POST /auth/refresh` — Refresh token

### Keys
- `GET /keys` — List key (query: `page`, `limit`, `status`, `search`, `sort`)
- `POST /keys/generate` — Generate key (`count`, `duration`, `device_id`, `notes`)
- `GET /keys/:id` — Detail key
- `PATCH /keys/:id` — Update key
- `DELETE /keys/:id` — Hapus key
- `PUT /keys/:id/revoke` — Revoke key
- `PUT /keys/:id/activate` — Aktifkan kembali
- `GET /keys/validate/:key_value` — Validasi key

### Dashboard
- `GET /dashboard` — Statistik (`totalKeys`, `activeKeys`, `expiredKeys`, `revokedKeys`, `onlineDevices`)

### Admin
- `GET /admin/activity` — Riwayat aktivitas admin

### Export
- `GET /export/csv` — Export CSV
- `GET /export/json` — Export JSON

## Response Format
```json
{
  "data": [...],
  "message": "...",
  "pagination": { "page": 1, "limit": 20 }
}
```

## Error Format
```json
{
  "error": "Pesan error",
  "status": 404
}
```
