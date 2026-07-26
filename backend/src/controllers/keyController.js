const pool = require('../config/database');
const logger = require('../utils/logger');
const { generateKeyString } = require('../utils/keyGenerator');

// GET /api/keys — semua key dengan pagination, filter, search
exports.getAllKeys = async (req, res) => {
    try {
        const { page = 1, limit = 20, status, search, sort = 'created_at DESC' } = req.query;
        const offset = (page - 1) * limit;
        let query = 'SELECT * FROM keys';
        const params = [];
        const conditions = [];

        if (status && status !== 'all') {
            conditions.push(`status = $${params.length + 1}`);
            params.push(status);
        }
        if (search) {
            conditions.push(`(key_value ILIKE $${params.length + 1} OR notes ILIKE $${params.length + 1})`);
            params.push(`%${search}%`);
        }
        if (conditions.length > 0) {
            query += ' WHERE ' + conditions.join(' AND ');
        }
        query += ` ORDER BY ${sort} LIMIT $${params.length + 1} OFFSET $${params.length + 2}`;
        params.push(parseInt(limit), parseInt(offset));

        const result = await pool.query(query, params);
        res.json({ data: result.rows, pagination: { page: parseInt(page), limit: parseInt(limit) } });
    } catch (err) {
        logger.error(err);
        res.status(500).json({ error: 'Gagal mengambil daftar key' });
    }
};

// POST /api/keys/generate — generate satu atau bulk
exports.generateKey = async (req, res) => {
    try {
        const { count = 1, duration, device_id, user_name, notes, custom_key } = req.body;
        const keys = [];

        for (let i = 0; i < Math.min(parseInt(count), 1000); i++) {
            const keyValue = custom_key || generateKeyString();
            const durationDays = duration || 30;
            const result = await pool.query(
                `INSERT INTO keys (key_value, status, duration_days, device_id, user_name, notes, created_at, expired_at) VALUES ($1, $2, $3, $4, $5, $6, NOW(), NOW() + INTERVAL '${durationDays} days') RETURNING *`,
                [keyValue, 'active', durationDays, device_id || null, user_name || null, notes || null]
            );
            keys.push(result.rows[0]);
        }
        res.status(201).json({ message: `${keys.length} key berhasil digenerate`, data: keys });
    } catch (err) {
        logger.error(err);
        res.status(500).json({ error: 'Gagal generate key' });
    }
};

// GET /api/keys/:id — detail satu key
exports.getKeyById = async (req, res) => {
    try {
        const result = await pool.query('SELECT * FROM keys WHERE id = $1', [req.params.id]);
        if (result.rows.length === 0) return res.status(404).json({ error: 'Key tidak ditemukan' });
        res.json({ data: result.rows[0] });
    } catch (err) {
        logger.error(err);
        res.status(500).json({ error: 'Gagal mengambil detail key' });
    }
};

// PATCH /api/keys/:id — edit key
exports.updateKey = async (req, res) => {
    try {
        const { status, notes, duration_days, device_id, user_name } = req.body;
        const fields = [];
        const params = [];
        let index = 1;

        if (status !== undefined) { fields.push(`status = $${index++}`); params.push(status); }
        if (notes !== undefined) { fields.push(`notes = $${index++}`); params.push(notes); }
        if (duration_days !== undefined) { fields.push(`duration_days = $${index++}`); params.push(duration_days); }
        if (device_id !== undefined) { fields.push(`device_id = $${index++}`); params.push(device_id); }
        if (user_name !== undefined) { fields.push(`user_name = $${index++}`); params.push(user_name); }

        if (fields.length === 0) return res.status(400).json({ error: 'Tidak ada data yang diupdate' });
        params.push(req.params.id);
        const query = `UPDATE keys SET ${fields.join(', ')} WHERE id = $${index}`;
        await pool.query(query, params);
        res.json({ message: 'Key berhasil diupdate' });
    } catch (err) {
        logger.error(err);
        res.status(500).json({ error: 'Gagal update key' });
    }
};

// DELETE /api/keys/:id — hapus key
exports.deleteKey = async (req, res) => {
    try {
        await pool.query('DELETE FROM keys WHERE id = $1', [req.params.id]);
        res.json({ message: 'Key berhasil dihapus' });
    } catch (err) {
        logger.error(err);
        res.status(500).json({ error: 'Gagal menghapus key' });
    }
};

// PUT /api/keys/:id/revoke — revoke key
exports.revokeKey = async (req, res) => {
    try {
        await pool.query("UPDATE keys SET status = 'revoked' WHERE id = $1", [req.params.id]);
        res.json({ message: 'Key berhasil direvoke' });
    } catch (err) {
        logger.error(err);
        res.status(500).json({ error: 'Gagal revoke key' });
    }
};

// PUT /api/keys/:id/activate — aktifkan kembali
exports.activateKey = async (req, res) => {
    try {
        await pool.query("UPDATE keys SET status = 'active' WHERE id = $1", [req.params.id]);
        res.json({ message: 'Key berhasil diaktifkan kembali' });
    } catch (err) {
        logger.error(err);
        res.status(500).json({ error: 'Gagal mengaktifkan key' });
    }
};

// GET /api/keys/validate/:key_value
exports.validateKey = async (req, res) => {
    try {
        const result = await pool.query('SELECT * FROM keys WHERE key_value = $1', [req.params.key_value]);
        if (result.rows.length === 0) return res.status(404).json({ valid: false, message: 'Key tidak ditemukan' });
        const key = result.rows[0];
        if (key.status === 'revoked') return res.json({ valid: false, message: 'Key sudah direvoke', data: key });
        if (new Date(key.expired_at) < new Date()) return res.json({ valid: false, message: 'Key sudah expired', data: key });
        res.json({ valid: true, message: 'Key valid', data: key });
    } catch (err) {
        logger.error(err);
        res.status(500).json({ valid: false, error: 'Gagal validasi key' });
    }
};
