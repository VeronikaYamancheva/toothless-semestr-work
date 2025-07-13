import { createApp } from 'vue';
import { createPinia } from 'pinia';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';
import App from './App.vue';
import router from './router';
import { useAuthStore } from '@/store/auth.store';
import AlertPlugin from "@/utils/plugins/AlertPlugin.js";
import preventSubmit from './directives/preventSubmit'
import {setupInterceptors} from "@/api/interceptors";
import apiClient from "@/api/axios";

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

const app = createApp(App);

app.use(pinia);
app.use(router);
app.use(AlertPlugin)

setupInterceptors(apiClient)

const authStore = useAuthStore();
authStore.init()

if (!authStore.accessToken) {
    authStore.refresh().catch(() => {
        console.log("Refresh failed at main.js")
    })
}

app.mount('#app');
