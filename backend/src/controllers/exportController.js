const pool = require('../config/database');
const logger = require('../utils/logger');

exports.exportCsv = async (req, res) => {
    try {
        const result = await pool.query('SELECT * FROM keys');
        let csv = 'id,key_value,status,duration_days,device_id,user_name,notes,created_at,expired_at\n';
        result.rows.forEach(row => {
            csv += `${row.id},${row.key_value},${row.status},${row.duration_days},${row.device_id || ''},${row.user_name || ''},"${row.notes || ''}",${row.created_at},${row.expired_at}\n`;
        });
        res.setHeader('Content-Type', 'text/csv');
        res.setHeader('Content-Disposition', 'attachment; filename=keys_export.csv');
        res.send(csv);
    } catch (err) {
        logger.error(err);
        res.status(500).json({ error: 'Gagal export CSV' });
    }
};

exports.exportJson = async (req, res) => {
    try {
        const result = await pool.query('SELECT * FROM keys');
        res.json({ data: result.rows });
    } catch (err) {
        logger.error(err);
        res.status(500).json({ error: 'Gagal export JSON' });
    }
};
