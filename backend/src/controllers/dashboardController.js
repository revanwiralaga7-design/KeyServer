const pool = require('../config/database');
const logger = require('../utils/logger');

exports.getDashboardStats = async (req, res) => {
    try {
        const total = await pool.query('SELECT COUNT(*) FROM keys');
        const active = await pool.query("SELECT COUNT(*) FROM keys WHERE status = 'active'");
        const expired = await pool.query("SELECT COUNT(*) FROM keys WHERE status = 'expired'");
        const revoked = await pool.query("SELECT COUNT(*) FROM keys WHERE status = 'revoked'");
        res.json({
            totalKeys: parseInt(total.rows[0].count),
            activeKeys: parseInt(active.rows[0].count),
            expiredKeys: parseInt(expired.rows[0].count),
            revokedKeys: parseInt(revoked.rows[0].count)
        });
    } catch (err) {
        logger.error(err);
        res.status(500).json({ error: 'Gagal mengambil statistik' });
    }
};
