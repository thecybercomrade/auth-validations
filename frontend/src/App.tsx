import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import axios from "axios";
import Dashboard from "./components/Dashboard";

const App: React.FC = () => {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean | null>(null);

  useEffect(() => {

    axios
      .get("http://localhost:3000/auth/verify", { withCredentials: true })
      .then(() => setIsAuthenticated(true))
      .catch(() => {
        axios.post("http://localhost:3000/auth/refresh", {}, { withCredentials: true })
          .then(() => setIsAuthenticated(true))
          .catch(() => {
            console.error("User is not authenticated");
          });
        setIsAuthenticated(false);
        window.location.href = "http://localhost:3000/auth/login"; // Redirect to Auth0 login page
      });
  }, []);

  if (isAuthenticated === null) {
    return <div>Loading...</div>; // Show loading state while checking authentication
  }

  return (
    <Router>
      <Routes>
        <Route path="*" element={isAuthenticated ? <Dashboard /> : <Navigate to="/" replace />} />
        <Route path="/" element={<Navigate to="/dashboard" replace />} />
        <Route path="/dashboard" element={isAuthenticated ? <Dashboard /> : <Navigate to="/" replace />} />
      </Routes>
    </Router>
  );
};

export default App;
