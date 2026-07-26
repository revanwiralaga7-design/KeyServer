const express = require('express');
const router = express.Router();
const controller = require('../controllers/adminController');

router.get('/activity', controller.getActivityHistory);

module.exports = router;
