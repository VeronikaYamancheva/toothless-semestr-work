import apiClient from "@/api/axios";
import router from "@/router";

export const CommentsService = {
    async comments(blocksCount) {
        try {
            const response = await apiClient.get('/comments', {
                params: { blocksCount },
            });
            return response.data;
        } catch (error) {
            console.log(error);

        }
    },
    async replies(commentId) {
        try {
            const response = await apiClient.get(`/comments/${commentId}/replies`, {
                params: { commentId },
            });
            return response.data;
        } catch (error) {
            console.log(error.response);
        }
    },
    async sendComment(content, dentistId, parentId) {
        try {

            const formData = new FormData();
            formData.append("content", content);
            if (dentistId) {
                formData.append("dentistId", dentistId);
            }
            if (parentId) {
                formData.append("parentId", parentId);
            }
            const response = await apiClient.post(`/comments`, formData);
            return response.data;
        } catch (error) {
            console.log( error.response);

        }
    },
    async deleteComment(commentId) {
        try {

            const response = await apiClient.delete(`/comments/${commentId}`);
            return response.data;
        } catch (error) {
            console.log( error.response);

        }
    },
    async getAllByDentistId(dentistId, blocksCount) {
        try {
            const response = await apiClient.get(`/comments/dentist/${dentistId}`, {
                params: { blocksCount },
            });
            return response.data;
        } catch (error) {
            console.log( error.response);

        }
    },
}