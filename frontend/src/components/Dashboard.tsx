import React, { useEffect } from "react";
import axios from "axios";
import { sendGetRequest } from "../services/appServices";

const Dashboard: React.FC = () => {
  const logout = () => {
    axios
      .get("http://localhost:3000/auth/logout", { withCredentials: true })
      .then(() => {
        console.log("User logged out. Redirecting to Auth0 login...");
        window.location.href = "http://localhost:3000/auth/login"; // Redirect to Auth0 login page
      })
      .catch((error) => {
        console.error("Logout failed:", error);
      });
  };

  useEffect(() => {

  }, []); // Empty dependency array to run only once on mount

  return (
    <div className="h-screen flex flex-col">
      {/* Navbar */}
      <nav className="bg-blue-600 text-white p-4 flex justify-between items-center shadow-lg">
        <h2 className="text-xl font-semibold">Dashboard</h2>
        <button
          onClick={logout}
          className="bg-red-500 px-4 py-2 rounded-md hover:bg-red-600"
        >
          Logout
        </button>
      </nav>

      {/* Main Content */}
      <div className="flex flex-grow items-center justify-center">
        <div className="text-center">
          <h1 className="text-3xl font-bold mb-6">Welcome to Dashboard</h1>
          <div className="space-x-4">
            <button className="bg-green-500 text-white px-6 py-3 rounded-md hover:bg-green-600"
            onClick={() => sendGetRequest()}
            >
              Action 1
            </button>
            <button className="bg-blue-500 text-white px-6 py-3 rounded-md hover:bg-blue-600">
              Action 2
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
