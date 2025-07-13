import axios from 'axios';
import {setupInterceptors} from "@/api/interceptors";

const apiClient = axios.create({
    baseURL: 'http://localhost:8080/api/v1',
    withCredentials: true,
    headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
    }
});

setupInterceptors(apiClient);

export default apiClient;