const express = require('express');
const router = express.Router();
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const pool = require('../config/database');
const logger = require('../utils/logger');

router.post('/login', async (req, res) => {
    try {
        const { username, password } = req.body;
        if (!username || !password) {
            return res.status(400).json({ error: 'Username dan password diperlukan' });
        }

        const result = await pool.query('SELECT * FROM admins WHERE username = $1', [username]);
        const admin = result.rows[0];

        if (!admin) {
            logger.warn(`Login gagal: username ${username} tidak ditemukan`);
            return res.status(401).json({ error: 'Kredensial tidak valid' });
        }

        const valid = await bcrypt.compare(password, admin.password_hash);
        if (!valid) {
            logger.warn(`Login gagal: password salah untuk ${username}`);
            return res.status(401).json({ error: 'Kredensial tidak valid' });
        }

        const token = jwt.sign(
            { id: admin.id, username: admin.username, role: admin.role },
            process.env.JWT_SECRET || 'keyserver-secret-change-me',
            { expiresIn: '24h' }
        );

        logger.info(`Login berhasil: ${username}`);
        res.json({ token, admin: { id: admin.id, username: admin.username, role: admin.role } });
    } catch (err) {
        logger.error(err);
        res.status(500).json({ error: 'Server error saat login' });
    }
});

router.post('/refresh', (req, res) => {
    try {
        const authHeader = req.headers['authorization'];
        const token = authHeader && authHeader.split(' ')[1];
        if (!token) return res.status(401).json({ error: 'Token diperlukan' });

        jwt.verify(token, process.env.JWT_SECRET || 'keyserver-secret-change-me', (err, user) => {
            if (err) return res.status(403).json({ error: 'Token tidak valid' });
            const newToken = jwt.sign(
                { id: user.id, username: user.username, role: user.role },
                process.env.JWT_SECRET || 'keyserver-secret-change-me',
                { expiresIn: '24h' }
            );
            res.json({ token: newToken });
        });
    } catch (err) {
        res.status(500).json({ error: 'Server error saat refresh' });
    }
});

module.exports = router;
