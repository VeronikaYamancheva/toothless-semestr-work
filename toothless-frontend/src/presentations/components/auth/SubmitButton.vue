<template>
  <button
      type="submit"
      class="submit-button"
      :disabled="loading"
  >
    <span class="submit-button-title" v-if="!loading">{{ label }}</span>
    <span class="submit-button-title" v-else>Загрузка...</span>
  </button>
</template>

<script setup>
/* global defineProps */

defineProps({
  loading: {
    type: Boolean,
    default: false
  },
  label: {
    type: String,
    default: ''
  }
});
</script>

<style scoped>

@import "@/assets/css/auth/auth-submit-button.css";

</style>