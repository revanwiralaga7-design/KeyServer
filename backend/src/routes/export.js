const express = require('express');
const router = express.Router();
const controller = require('../controllers/exportController');

router.get('/csv', controller.exportCsv);
router.get('/json', controller.exportJson);

module.exports = router;
