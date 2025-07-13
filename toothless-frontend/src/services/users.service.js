import apiClient from "@/api/axios";

export const UsersService = {
    async getAllUsers(page, size) {

        try {
            const response = await apiClient.get('/users', {
                params: { page, size }
            });
            return response;
        } catch (err) {
        }

    }
}