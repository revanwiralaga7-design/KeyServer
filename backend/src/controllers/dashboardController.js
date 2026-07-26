const pool = require('../config/database');
const logger = require('../utils/logger');

exports.getStats = async (req, res) => {
    try {
        const queries = {
            total: await pool.query('SELECT COUNT(*) FROM keys'),
            active: await pool.query("SELECT COUNT(*) FROM keys WHERE status = 'active'"),
            expired: await pool.query("SELECT COUNT(*) FROM keys WHERE status = 'expired' OR expired_at < NOW()"),
            revoked: await pool.query("SELECT COUNT(*) FROM keys WHERE status = 'revoked'"),
            online: await pool.query('SELECT COUNT(DISTINCT device_id) FROM keys WHERE status = \'active\' AND device_id IS NOT NULL')
        };
        res.json({
            totalKeys: parseInt(queries.total.rows[0].count),
            activeKeys: parseInt(queries.active.rows[0].count),
            expiredKeys: parseInt(queries.expired.rows[0].count),
            revokedKeys: parseInt(queries.revoked.rows[0].count),
            onlineDevices: parseInt(queries.online.rows[0].count)
        });
    } catch (err) {
        logger.error(err);
        res.status(500).json({ error: 'Gagal mengambil statistik dashboard' });
    }
};
