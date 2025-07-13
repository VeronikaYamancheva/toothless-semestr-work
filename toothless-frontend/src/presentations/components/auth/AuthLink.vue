<template>
  <router-link :to="to" class="auth-link">
    <slot />
  </router-link>
</template>

<script setup>
/* global defineProps */
defineProps({
  to: {
    type: [String, Object],
    required: true
  }
});
</script>

<style scoped>

@import "@/assets/css/auth/auth-link.css";

</style>