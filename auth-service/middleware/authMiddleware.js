const jwt = require("jsonwebtoken");
const { JWT_SECRET } = require("../config");

const authenticateJWT = (req, res, next) => {
  const token = req.cookies.jwt;

  if (!token) {
    return res.status(401).json({ error: "Unauthorized" });
  }

  jwt.verify(token, JWT_SECRET, (err, decoded) => {
    console.log("Decoded JWT:", decoded);
    if (err) {
      console.log("JWT verification error:", err);
      return res.status(403).json({ error: "Invalid token" });
    }
    req.user = decoded;
    next();
  });
};

module.exports = { authenticateJWT };
