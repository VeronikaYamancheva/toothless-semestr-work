 import apiClient from '@/api/axios';
import router from "@/router";

export const AuthService = {
    async login(credentials) {
        try {
            const response = await apiClient.post('/auth/login', credentials);
            return response.data;
        } catch (error) {
            switch (error.response.status) {
                case 400 :
                    const errorMessage = error.response.data?.errorMessage || 'Invalid data. Please check your inputs.';
                    alert(errorMessage);
                    break;
                case 403 :
                    if (error.response.data?.errorMessage === 'Email is not verified') {
                        alert('You don`t have already verified email');
                        router.push('/verify')
                        break;
                    }
                    await router.push('/profile');
                    alert('You are already an authorized user');
                    break;
            }
        }
    },

    async registerClient(userData) {
        try {
            const response = await apiClient.post('/auth/register/client', userData);
            return response.data;
        } catch (error) {
            switch (error.response.status) {
                case 400 :
                    const errorMessage = error.response.data?.errorMessage || 'Invalid data. Please check your inputs.';
                    alert(errorMessage);
                    break;
                case 403 :
                    await router.push('/profile');
                    alert('You are already an authorized user');
                    break;
            }
        }
    },

    async registerDentist(userData) {
        try {
            const response = await apiClient.post('/auth/register/dentist', userData);
            return response.data;
        } catch (error) {
            switch (error.response.status) {
                case 400 :
                    const errorMessage = error.response.data?.errorMessage || 'Invalid data. Please check your inputs.';
                    alert(errorMessage);
                    break;
                case 403 :
                    await router.push('/profile');
                    alert('You are already an authorized user');
                    break;
            }
        }
    },

    async verify(code) {
        try {
            const response = await apiClient.post('/auth/verify', null, {
                params: { code },
            });
            return response.data;
        } catch (error) {
            switch (error.response.status) {
                case 400 :
                    const errorMessage = error.response.data?.errorMessage || 'Invalid data. Please check your inputs.';
                    alert(errorMessage);
                    break;
                case 403 :
                    await router.push('/profile');
                    alert('You are already an authorized user');
                    break;
                case 404 :
                    alert('We can`t find user with your userId');
                    break;
            }
        }
    },

    async checkEmailAvailability(email) {
        try {
            const response = await apiClient.get('/auth/email_check', { params: { email } });
            return response.data;
        } catch (error) {
            console.log(error);
        }
    },

    async refresh() {
        try {
            const response = await apiClient.post('/auth/refresh', {}, {
                withCredentials: true,
            });
            return response.data;
        } catch (error) {
            throw error;
        }
    },

    async logout() {
        try {
            await apiClient.post('/auth/logout');
        } catch (error) {
            throw error;

        }
    },


};