<script setup>

import githubIcon from "@/assets/images/github-mark.svg";
import googleIcon from "@/assets/images/google-g-logo.svg";
import {inject, onMounted, ref, watch} from "vue";
import {useAuthStore} from "@/store/auth.store";

const authStore = useAuthStore()
const redirectToGithub = () => {
  window.location.href = `http://localhost:8080/oauth2/authorization/github?link=true&userId=${authStore.user.userId}`;
};

const labels = defineProps({
  github: {
    type: String,
    default: 'Continue with GitHub',
  },
  google: {
    type: String,
    default: 'Continue with Google',
  },
  providers: Array
})
const withGithub = ref()
const withGoogle = ref()

const shouldLoad = inject('shouldLoad')
const data = ref(null)

watch(shouldLoad, async (newVal) => {
  if (newVal) {
    await loadProviders()
  }
})

const loadProviders = () => {
  if (labels.providers.authProviders) {
    console.log("SrdhjfvbohsegfoyuaESF")
  }
  for (const provider in labels.providers) {
    if (labels.providers[provider].authProvider === "GITHUB") {
      withGithub.value = true
    }
    else if (labels.providers[provider].authProvider === "GOOGLE") {
      withGoogle.value = true

    }
  }
}

const redirectToGoogle = () => {
  window.location.href = `http://localhost:8080/oauth2/authorization/google?link=true&userId=${authStore.user.userId}`;
};

</script>

<template>

  <div class="profile-decoration-line" />
  <div class="profile-field">
    <span class="profile-field-label">
      Google Provider
    </span>
    <span v-if="withGoogle" class="profile-field-info-no-data">
      You authenticated with Google!
    </span>
    <span v-if="!withGoogle" class="profile-field-info-no-data">
      You haven't authenticated with Google yet.
    </span>

  </div>

  <div class="profile-decoration-line" />
  <div class="profile-field">
    <span class="profile-field-label">
      GitHub Provider
    </span>
    <span v-if="withGithub" class="profile-field-info-no-data">
      You authenticated with GitHub!
    </span>

    <span v-if="!withGithub" class="profile-field-info-no-data">
      You haven't authenticated with GitHub yet.
    </span>

  </div>
  <div class="profile-decoration-line" />

</template>

<style scoped>

</style>