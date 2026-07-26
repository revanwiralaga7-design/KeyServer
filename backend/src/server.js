const express = require('express');
const cors = require('cors');
const helmet = require('helmet');
const rateLimit = require('express-rate-limit');
const dotenv = require('dotenv');

const authRoutes = require('./routes/auth');
const keyRoutes = require('./routes/keys');
const dashboardRoutes = require('./routes/dashboard');

const logger = require('./utils/logger');
const { authenticateToken } = require('./middleware/auth');

dotenv.config();

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware global
app.use(helmet());
app.use(cors());
app.use(express.json({ limit: '1mb' }));
app.use(express.urlencoded({ extended: true }));

// Rate limiter
const limiter = rateLimit({
    windowMs: 15 * 60 * 1000,
    max: 100,
    message: 'Terlalu banyak request dari IP ini, coba lagi nanti.'
});
app.use('/api/', limiter);

// Logging request
app.use((req, res, next) => {
    logger.info(`${req.method} ${req.originalUrl}`);
    next();
});

// Routes
app.use('/api/auth', authRoutes);
app.use('/api/keys', authenticateToken, keyRoutes);
app.use('/api/dashboard', authenticateToken, dashboardRoutes);

// Health check
app.get('/health', (req, res) => {
    res.json({ status: 'ok', service: 'Key Server Manager' });
});

// Global error handler
app.use((err, req, res, next) => {
    logger.error(err.stack || err.message);
    const status = err.statusCode || 500;
    res.status(status).json({
        error: err.message || 'Internal Server Error',
        status
    });
});

app.listen(PORT, () => {
    logger.info(`Server berjalan di port ${PORT}`);
    console.log(`Key Server Manager backend: http://localhost:${PORT}`);
});
