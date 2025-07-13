<script setup>

import githubIcon from "@/assets/images/github-mark.svg";
import googleIcon from "@/assets/images/google-g-logo.svg";

const redirectToGithub = () => {
  window.location.href = 'http://localhost:8080/oauth2/authorization/github';
};

const labels = defineProps({
  github: {
    type: String,
    default: 'Continue with GitHub',
  },
  google: {
    type: String,
    default: 'Continue with Google',
  }

})

const redirectToGoogle = () => {
  window.location.href = 'http://localhost:8080/oauth2/authorization/google';
};

</script>

<template>
  <div class="oauth-component">
    <button type="button" class="oauth-button" @click="redirectToGithub">
      <img class="oauth-button-img" :src="githubIcon" alt="GitHub Icon">
      <span class="oauth-button-title">{{ github }}</span>
    </button>

    <button type="button" class="oauth-button"  @click="redirectToGoogle">
      <img class="oauth-button-img" :src="googleIcon" alt="Google Icon">
      <span class="oauth-button-title">{{ google }}</span>
    </button>
  </div>
</template>

<style scoped>

</style>