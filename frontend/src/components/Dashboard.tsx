import React from "react";
import axios from "axios";

const Dashboard: React.FC = () => {
  const logout = () => {
    axios.get("http://localhost:3000/auth/logout", { withCredentials: true })
      .then(() => window.location.href = "http://localhost:3000/auth/login");
  };

  return (
    <div>
      <h1>Welcome to Dashboard</h1>
      <button onClick={logout}>Logout</button>
    </div>
  );
};

export default Dashboard;
