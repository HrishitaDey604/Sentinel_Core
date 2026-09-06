import axios from 'axios';
import { refreshAccessToken } from './authApi';

const axiosInstance = axios.create();

axiosInstance.interceptors.request.use((config) => {
    const token = localStorage.getItem('accessToken');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

axiosInstance.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config;

        if (error.response?.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;

            const refreshToken = localStorage.getItem('refreshToken');
            if (refreshToken) {
                try {
                    const res = await refreshAccessToken(refreshToken);
                    const newAccessToken = res.data.accessToken;
                    localStorage.setItem('accessToken', newAccessToken);

                    originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
                    return axiosInstance(originalRequest);
                } catch (refreshError) {
                    localStorage.removeItem('accessToken');
                    localStorage.removeItem('refreshToken');
                    window.location.reload();
                }
            }
        }

        return Promise.reject(error);
    }
);

export default axiosInstance;