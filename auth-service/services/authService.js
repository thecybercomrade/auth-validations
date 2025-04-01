const jwt = require("jsonwebtoken");
const axios = require("axios");
const {
  AUTH0_DOMAIN,
  AUTH0_CLIENT_ID,
  AUTH0_CLIENT_SECRET,
  AUTH0_CALLBACK_URL,
  JWT_SECRET,
  REFRESH_SECRET,
} = require("../config");

const login = (req, res) => {
  const authUrl = `https://${AUTH0_DOMAIN}/authorize?response_type=code&client_id=${AUTH0_CLIENT_ID}&redirect_uri=${AUTH0_CALLBACK_URL}&scope=openid profile email`;
  res.redirect(authUrl);
};

const callback = async (req, res) => {
  try {
    const { code } = req.query;
    const tokenUrl = `https://${AUTH0_DOMAIN}/oauth/token`;

    const response = await axios.post(tokenUrl, {
      grant_type: "client_credentials",
      client_id: AUTH0_CLIENT_ID,
      client_secret: AUTH0_CLIENT_SECRET,
      code,
      redirect_uri: AUTH0_CALLBACK_URL,
    });

    const { access_token, refresh_token, id_token } = response.data;

    const jwtToken = jwt.sign({ access_token, id_token }, JWT_SECRET, {
      expiresIn: "1h",
    });
    const refreshToken = jwt.sign({ refresh_token }, REFRESH_SECRET, {
      expiresIn: "7d",
    });

    res.cookie("jwt", jwtToken, { httpOnly: true });
    res.cookie("refreshToken", refreshToken, { httpOnly: true });

    res.redirect("http://localhost:5173/dashboard");
  } catch (error) {
    res.status(500).json({ error: "Authentication failed" });
  }
};

const refreshToken = (req, res) => {
  const { refreshToken } = req.cookies;

  if (!refreshToken) return res.status(401).json({ error: "No refresh token" });

  jwt.verify(refreshToken, REFRESH_SECRET, (err, decoded) => {
    if (err) return res.status(403).json({ error: "Invalid refresh token" });

    const newJwtToken = jwt.sign(
      { access_token: decoded.refresh_token },
      JWT_SECRET,
      { expiresIn: "1h" }
    );
    res.cookie("jwt", newJwtToken, { httpOnly: true });

    res.json({ message: "Token refreshed" });
  });
};

const logout = (req, res) => {
  res.clearCookie("jwt");
  res.clearCookie("refreshToken");
  res.json({ message: "Logged out" });
};

const status = (req, res) => {
  const token = req.cookies.jwt; // Get token from cookies

  if (!token) {
    return res.status(401).json({ message: "No token found. Please log in." });
  }

  // Verify JWT using Auth0 public key
  jwt.verify(
    token,
    { audience: CLIENT_ID, issuer: `https://${AUTH0_DOMAIN}/` },
    (err, decoded) => {
      if (err) {
        return res.status(401).json({ message: "Invalid or expired token." });
      }
      res.status(200).json({ message: "Authenticated", user: decoded });
    }
  );
};
module.exports = { login, callback, refreshToken, logout, status };
