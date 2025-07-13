<template>
  <div>
    <button class="button-logout" v-if="isAuthenticated" @click="showModalWindow = true">Log out</button>

    <div v-if="showModalWindow" class="modal-window-overlay" @click.self="showModalWindow = false">
      <div class="modal-window-field">
        <div class="modal-window-item-right">
          <button class="modal-window-close-button" @click="showModalWindow = false">×</button>
        </div>

        <div >
          <span class="modal-window-field-title">Вы уверены, что хотите выйти?</span>
        </div>

        <div class="modal-window-item-center">
            <button class="modal-window-field-button" @click="logout()">Да</button>
            <button class="modal-window-field-button" @click="showModalWindow = false">Нет</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>

/* global defineProps */

import { useAuthStore } from '@/store/auth.store';

defineProps({
  isAuthenticated: {
    type: Boolean,
    default: false
  }
});

import {inject, ref} from 'vue'
import {useRouter} from "vue-router";

const router = useRouter()
const alert = inject('alert')
const authStore = useAuthStore();
const showModalWindow = ref(false)

const logout = () => {
  showModalWindow.value = false
  authStore.logout();
  alert('You have successfully completed the session!')
  router.push('/')
};

</script>

<style scoped>

@import '@/assets/css/constants/modal-window.css';

</style>