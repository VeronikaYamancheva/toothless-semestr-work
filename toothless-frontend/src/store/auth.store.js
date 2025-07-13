import { defineStore } from 'pinia';
import { AuthService } from '@/services/auth.service';
import {getUserFromToken} from "@/services/jwt.parser";


export const useAuthStore = defineStore('auth', {
    state: () => ({
        accessToken: localStorage.getItem('accessToken'),
        isRefreshing: false,
        isAuthenticated: false,
        refreshPromise: null,
        user: null,

    }),
    actions: {
        async init() {
            this.accessToken = localStorage.getItem('accessToken')
            if (this.accessToken) {
                this.isAuthenticated = true;
                await this.loadUser();
            } else {
                this.isAuthenticated = false;
                this.user = null;
            }
        },
        register(userUUID) {
            this.isAuthenticated = false;
        },
        login(tokenResponse) {
            this.accessToken = tokenResponse.accessToken;
            this.isAuthenticated = true;
            localStorage.setItem('accessToken', tokenResponse.accessToken);
            this.loadUser()
        },
        async logout() {
            try {
                await AuthService.logout();
            } catch (error) {
                console.error(error);
            } finally {
                this.accessToken = null;
                this.isAuthenticated = false;
                localStorage.removeItem('accessToken');
            }
        },
        async loadUser() {
            const token = localStorage.getItem('accessToken');
            if (!token) {
                this.clear();
                return;
            }
            this.user = await getUserFromToken(token);
        },
        async refresh() {
            if (this.isRefreshing) {
                return this.refreshPromise;
            }
            this.isRefreshing = true;
            try {
                this.refreshPromise = AuthService.refresh();
                const tokenResponse  = await this.refreshPromise;
                this.login(tokenResponse);
                return tokenResponse;
            } catch (error) {
                throw error;
            } finally {
                this.isRefreshing = false;
                this.refreshPromise = null;
            }
        },
        clear() {
            this.accessToken = null;
            this.isAuthenticated = false;
            localStorage.removeItem('accessToken');
        }
    }
});