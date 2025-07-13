import apiClient from "@/api/axios";

export const AdminService = {
    async getAllUsers(page, size) {
        try {
            const response = await apiClient.get(`/admin/users`, {
                params: { page, size }
            });
            return response.data;
        } catch (err) {
            console.log(err)
        }

    },
    async updateUserRole(userId, isAdmin) {
        try {
            const response = await apiClient.patch(`/admin/users/${userId}/admin`, {}, {
                params: { isAdmin }
            });
            return response.data;
        } catch (err) {
            console.log(err)
        }

    },
    async updateUserState(userId, isBanned) {
        try {
            const response = await apiClient.patch(`/admin/users/${userId}/ban`, {}, {
                params: { isBanned }
            });
            return response.data;
        } catch (err) {
            console.log(err)
        }

    },
    async getDentistsAccept() {
        try {
            const response = await apiClient.get(`/admin/dentist`);
            return response.data;
        } catch (err) {
            console.log(err)
        }

    },
    async acceptDentist(userId, isApproved) {
        try {
            const response = await apiClient.post(`/admin/dentist/${userId}`, {}, {
                params: { isApproved }
            });
            return response.data;
        } catch (err) {
            console.log(err)
        }

    },
}