const express = require('express');
const router = express.Router();
const pool = require('../config/database');
const logger = require('../utils/logger');

router.get('/', async (req, res) => {
    try {
        const result = await pool.query('SELECT id, key_value, status, created_at, expired_at FROM keys ORDER BY created_at DESC LIMIT 50');
        res.json({ data: result.rows });
    } catch (err) {
        logger.error(err);
        res.status(500).json({ error: 'Gagal mengambil data key' });
    }
});

router.post('/generate', async (req, res) => {
    try {
        const { duration, device_id } = req.body;
        const result = await pool.query(
            'INSERT INTO keys (key_value, status, duration_days, device_id) VALUES ($1, $2, $3, $4) RETURNING *',
            [`key-${Date.now()}`, 'active', duration || 30, device_id || null]
        );
        res.status(201).json({ data: result.rows[0] });
    } catch (err) {
        logger.error(err);
        res.status(500).json({ error: 'Gagal generate key' });
    }
});

module.exports = router;
