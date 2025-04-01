const express = require("express");
const { login, callback, refreshToken, logout, status } = require("../services/authService");
const { authenticateJWT } = require("../middleware/authMiddleware");

const router = express.Router();

router.get("/login", login);
router.get("/callback", callback);
router.post("/refresh", refreshToken);
router.get("/logout", logout);
router.get("/status", status);
router.get("/verify", authenticateJWT, (req, res) => {
  res.json({ message: "Token is valid", user: req.user });
});

module.exports = router;
