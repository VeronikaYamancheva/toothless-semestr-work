import apiClient from "@/api/axios";

export const AppointmentService = {
    async createAppointment(formData) {
        try {
            const response = await apiClient.post(`/appointments`, formData);
            return response.data;
        } catch (err) {
        }

    },
    async getAppointmentsByDateRange(beginDate, endDate) {
        try {
            const response = await apiClient.get(`/appointments/admin`, {
                params: {
                    begin: beginDate,
                    end: endDate,
                }
            });
            return response.data;
        } catch (err) {
        }
    },
    async getAppointmentById(appointmentId) {
        try {
            const response = await apiClient.get(`/appointments/${appointmentId}/admin`, {
                params: {
                   appointmentId: appointmentId
                }
            });
            return response.data;
        } catch (err) {
        }
    },
    async getClientPastAppointments() {
        try {
            const response = await apiClient.get(`/appointments/client/past`);
            return response.data;
        } catch (err) {
        }
    },
    async getClientUpcomingAppointments() {
        try {
            const response = await apiClient.get(`/appointments/client/upcoming`);
            return response.data;
        } catch (err) {
        }
    },
    async getDentistPastAppointments() {
        try {
            const response = await apiClient.get(`/appointments/dentist/past`);
            return response.data;
        } catch (err) {
        }
    },
    async getDentistUpcomingAppointments() {
        try {
            const response = await apiClient.get(`/appointments/dentist/upcoming`);
            return response.data;
        } catch (err) {
        }
    },
    async getAppointmentFormById(appointmentId) {
        try {
            const response = await apiClient.get(`/appointments/${appointmentId}/form`);
            return response.data;
        } catch (err) {
        }
    },
    async updateAppointmentFormById(appointmentId, appointmentForm) {
        try {
            const response = await apiClient.put(`/appointments/${appointmentId}/form`, appointmentForm);
            return response.data;
        } catch (err) {
        }
    }

}