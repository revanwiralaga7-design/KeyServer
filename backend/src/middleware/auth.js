const jwt = require('jsonwebtoken');
const logger = require('../utils/logger');

function authenticateToken(req, res, next) {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];

    if (!token) {
        logger.warn('Akses ditolak: token tidak ditemukan');
        return res.status(401).json({ error: 'Token diperlukan' });
    }

    jwt.verify(token, process.env.JWT_SECRET || 'keyserver-secret-change-me', (err, user) => {
        if (err) {
            logger.warn('Token tidak valid');
            return res.status(403).json({ error: 'Token tidak valid' });
        }
        req.user = user;
        next();
    });
}

module.exports = { authenticateToken };
