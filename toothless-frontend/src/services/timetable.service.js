import apiClient from "@/api/axios";

export const TimetableService = {
    async getTimetable(dentistId) {

        try {
            const response = await apiClient.get(`/timetable/${dentistId}`);
            return response.data;
        } catch (err) {
        }

    },
}