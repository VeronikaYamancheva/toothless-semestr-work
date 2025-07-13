import apiClient from "@/api/axios";

export const DentistsService = {
    async getAllDentists(page, size) {

        try {
            const response = await apiClient.get('/dentists', {
                params: { page, size }
            });
            return response.data;
        } catch (err) {
        }

    },
    async getDentistsForHomePage() {

        try {
            const response = await apiClient.get('/dentists/demo');
            return response.data;
        } catch (err) {
        }

    },
    async getDentistById(dentistId) {

        try {
            const response = await apiClient.get(`/dentists/${dentistId}`);
            return response.data;
        } catch (err) {
        }

    },
    async getDentistByProcedureId(procedureId) {

        try {
            const response = await apiClient.get(`/dentists/procedure/${procedureId}`);
            return response.data;
        } catch (err) {
        }

    },
    async getDentistsSpecialization() {

        try {
            const response = await apiClient.get(`/dentists/specializations`);
            return response.data;
        } catch (err) {
            handleApiError(err)
        }

    }
}