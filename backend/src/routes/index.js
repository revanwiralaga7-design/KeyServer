const express = require('express');
const router = express.Router();

const authRoutes = require('./auth');
const keyRoutes = require('./keys');
const dashboardRoutes = require('./dashboard');

router.use('/auth', authRoutes);
router.use('/keys', require('../middleware/auth').authenticateToken, keyRoutes);
router.use('/dashboard', require('../middleware/auth').authenticateToken, dashboardRoutes);
router.use('/admin/history', require('../middleware/auth').authenticateToken, require('./admin'));
router.use('/export', require('../middleware/auth').authenticateToken, require('./export'));

module.exports = router;
