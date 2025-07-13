import apiClient from '@/api/axios';

export const ProfileService = {
    async getProfile() {
        const response = await apiClient.get('/profile');
        return response.data;
    },
    async getProfileAvatar(userId)
    {
        try {
            const response = await apiClient.get(`/photos/avatars/${userId}`, {
                responseType: 'arraybuffer',
                skipGlobalErrorHandler: true
            });
            return response.data;
        } catch (err) {
            if (err.response.status === 404 || 400) {
                return err.response.status
            }}
    },
    async updateProfileAvatar(file) {
        try {
            console.log(file);
            const response = await apiClient.post(`/photos/avatars`,  file, {
                headers: {
                    "Content-Type": "multipart/form-data"
                }
            });
            console.log(response.data);
            return response.data;
        } catch (err) {
            console.log(err);
        }
    },
    async updateBaseProfile(userUpdatedBaseProfileData){
        console.log(userUpdatedBaseProfileData)
        const response = await apiClient.put('/profile/base', userUpdatedBaseProfileData);
        return response.data;
    },
    async updateClientProfile(userUpdatedClientProfileData){
        const response = await apiClient.put('/profile/client', userUpdatedClientProfileData);
        return response.data;
    },
    async updateDentistProfile(userUpdatedDentistProfileData){
        const response = await apiClient.put('/profile/dentist', userUpdatedDentistProfileData);
        return response.data;
    },
    async updatePassword(userUpdatedPassword){
    const response = await apiClient.put('/profile', userUpdatedPassword);
    return response.data;
},


}