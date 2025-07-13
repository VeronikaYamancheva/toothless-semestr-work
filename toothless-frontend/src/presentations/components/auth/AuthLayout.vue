<template>
  <div class="auth-container">
    <div class="auth-form-card">
      <h2 class="auth-form-title">
        {{ formTitle }}
      </h2>
      <slot/>
    </div>
  </div>
</template>

<script setup>

/* global defineProps */

defineProps({
  formTitle: {
    type: String,
    default: ''
  }
});

</script>

<style scoped>

@import '@/assets/css/auth/auth-page.css';

</style>