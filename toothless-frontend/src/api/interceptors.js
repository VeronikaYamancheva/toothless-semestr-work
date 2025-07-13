import {useAuthStore} from '@/store/auth.store';
import {errorHandler} from "@/error/ErrorHandler";

let isRefreshing = false;
let failedQueue = [];


const processQueue = (error, token = null) => {
    failedQueue.forEach(prom => {
        error ? prom.reject(error) : prom.resolve(token)
    })
    failedQueue = []
};

export const setupInterceptors = (apiClient) => {

    apiClient.interceptors.request.use((config) => {
        const authStore = useAuthStore();
        if (authStore.accessToken) {
            config.headers.Authorization = `Bearer ${authStore.accessToken}`;
        }
        return config;
    }, (error) => Promise.reject(error));

    apiClient.interceptors.response.use(
        (response) => response,
        async (error) => {
            const originalRequest = error.config;
            const authStore = useAuthStore();

            if (originalRequest?.skipGlobalErrorHandler) {
                return Promise.reject(error);
            }

            if (error.response?.status === 401 && !originalRequest._isRetry) {

                if (isRefreshing) {
                    return new Promise( (resolve, reject) => {
                        failedQueue.push({ resolve, reject });
                    }).then((token) => {
                            originalRequest.headers.Authorization = `Bearer ${token}`;
                            return apiClient(originalRequest);
                        })
                        .catch((err) => Promise.reject(err));
                }

                originalRequest._isRetry = true;
                isRefreshing = true;

                try {
                    const newToken = await authStore.refresh();
                    processQueue(null, newToken.accessToken);

                    originalRequest.headers.Authorization = `Bearer ${newToken.accessToken}`;
                    return apiClient(originalRequest);
                } catch (refreshError) {
                    processQueue(refreshError, null);

                    authStore.clear()
                    localStorage.removeItem('accessToken')
                    router.push('/login')
                    return Promise.reject(refreshError);
                }
            }
            return errorHandler.handle(error)
        }
    );
};