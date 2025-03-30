import React, { useEffect } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import axios from "axios";
import Dashboard from "./components/Dashboard";

const App: React.FC = () => {
  useEffect(() => {
    axios.post("http://localhost:3000/auth/refresh", {}, { withCredentials: true })
      .then(response => console.log("Token refreshed", response))
      .catch(() => window.location.href = "http://localhost:3000/auth/login");
  }, []);

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigate to="/dashboard" />} />
        <Route path="/dashboard" element={<Dashboard />} />
      </Routes>
    </Router>
  );
};

export default App;
