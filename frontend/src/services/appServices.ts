import axios from "axios";

const API_BASE_URL = "http://localhost:5000"; // Java backend

export const sendPostRequest = async (token: string) => {
    try {
        const response = await axios.post(
            `${API_BASE_URL}/protected-endpoint`, 
            {},
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            }
        );
        return response.data;
    } catch (error) {
        console.error("Error calling API:", error);
        throw error;
    }
};
