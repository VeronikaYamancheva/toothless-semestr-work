<script setup>

/* global defineProps */

defineProps({
  title: {
    type: String,
    default: ''
  }
});

</script>

<template>
  <div class="profile-block">
    <span class="profile-title">{{ title }}</span>
    <slot/>
  </div>
</template>

<style scoped>

@import '@/assets/css/profile/user-profile.css';

</style>