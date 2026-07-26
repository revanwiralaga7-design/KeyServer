const pool = require('../config/database');
const logger = require('../utils/logger');

exports.getActivityHistory = async (req, res) => {
    try {
        const result = await pool.query('SELECT id, action, target_id, created_at FROM admin_activities ORDER BY created_at DESC LIMIT 50');
        res.json({ data: result.rows });
    } catch (err) {
        logger.error(err);
        res.status(500).json({ error: 'Gagal mengambil riwayat aktivitas' });
    }
};
