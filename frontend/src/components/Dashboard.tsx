import React from "react";
import { useAuth0 } from "@auth0/auth0-react";
import { sendPostRequest } from "../services/appServices";
import config from "../config";

const Dashboard: React.FC = () => {
    const { user, logout, getAccessTokenSilently } = useAuth0();

    const handleApiRequest = async () => {
        try {
            const token = await getAccessTokenSilently();
            const response = await sendPostRequest(token);
            alert("API Response: " + JSON.stringify(response));
        } catch (error) {
            console.error("API call failed:", error);
            alert("API call failed!");
        }
    };

    return (
        <div>
            <h1>Welcome, {user?.name}!</h1>
            <button onClick={handleApiRequest}>Send Request</button>
            <button onClick={() => logout({ logoutParams: { returnTo: config.LOGOUT_REDIRECT } })}>
                Logout
            </button>
        </div>
    );
};

export default Dashboard;
