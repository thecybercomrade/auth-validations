import axios from "axios";

const API_BASE_URL = "http://localhost:8080"; // Java backend

export const sendGetRequest = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/aggregator/userdetails`, {
            // headers: {
            //     Authorization: `Bearer ${token}`,
            // },
            withCredentials: true
        });
        return response.data;
    } catch (error) {
        console.error("Error calling API:", error);
        throw error;
    }
};
