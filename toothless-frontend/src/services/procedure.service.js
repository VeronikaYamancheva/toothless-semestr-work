import apiClient from "@/api/axios";

export const ProcedureService = {

    async getAllProcedures(page, size) {

        try {
            const response = await apiClient.get('/procedures', {
                params: { page, size }
            });
            return response.data;
        } catch (err) {
        }
    },

    async getProceduresForHomePage() {

        try {
            const response = await apiClient.get('/procedures/demo');
            return response.data;
        } catch (err) {
        }
    },

    async createNewProcedure(creatingFormData) {

        try {
            console.log("inside createNewProcedure")
            const response = await apiClient.post(`/procedures`, creatingFormData);
            return response.data;
        } catch (err) {
            handleApiError(err);
        }
    },

    async getProcedureById(id) {

        try {
            const response = await apiClient.get(`/procedures/${id}`);
            return response.data;
        } catch (err) {
        }
    },

    async updateProcedureById(id, updatingFormData) {

        try {
            const response = await apiClient.patch(`/procedures/${id}`, updatingFormData);
            return response.data;
        } catch (err) {
            handleApiError(err);
        }
    },

    async deleteProcedureById(id) {

        try {
            const response = await apiClient.delete(`/procedures/${id}`);
            return response.data;
        } catch (err) {
        }
    }
};
