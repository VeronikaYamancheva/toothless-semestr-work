<script setup>
import { useAuthStore } from '@/store/auth.store';
import { storeToRefs } from 'pinia';

import instagramBrightIcon from '@/assets/images/instagram-icon-white.svg'
import github from '@/assets/images/github-mark-white.svg'
import odnoklassniki from '@/assets/images/odnoklassniki.svg'
import vkontakte from '@/assets/images/vk-logo.svg'
import telegramIcon from '@/assets/images/telegram-icon-light-gradient.svg'
import gmailIcon from '@/assets/images/gmail-icon-gradient.svg'

const authStore = useAuthStore();
const { isAuthenticated } = storeToRefs(authStore);

const logout = () => {
  authStore.logout();
  window.location.href = '/login';
};

</script>

<template>
  <header class="footer">
    <div class="footer-container">
      <div class="footer-block">
        <span class="footer-text">
          Toothless LLC uses cookies in order to personalize the services and improve the usability of the website.
          "Cookies" are small files containing information about previous visits to a website.
        </span>
      </div>
    </div>

    <div class="footer-container">

      <div class="footer-block">
        <span class="footer-text">
            ⓒ 2024-2025, Toothless LLC, dental clinic
        </span>
      </div>

      <div class="footer-block">
        <span class="footer-text">
            Russia, Kazan, 420010, Universiade Village, 18
        </span>
      </div>

    </div>

    <div class="footer-container">
      <div class="footer-field">
        <img class="icon-img" :src="odnoklassniki" alt="Odnoklassniki Icon"/>
        <img class="icon-img" :src="vkontakte" alt="VKontakte Icon"/>
        <img class="icon-img" :src="telegramIcon" alt="Telegram Icon"/>
        <img class="icon-img" :src="instagramBrightIcon" alt="Instagram Icon"/>
        <img class="icon-img" :src="gmailIcon" alt="Gmail Icon"/>
        <img class="icon-img" :src="github" alt="GitHub Icon"/>
      </div>
    </div>
  </header>
</template>

<style scoped>

@import '@/assets/css/constants/footer-component.css';

</style>