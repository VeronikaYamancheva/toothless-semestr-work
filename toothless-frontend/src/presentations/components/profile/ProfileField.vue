<script setup>

/* global defineProps */

defineProps({
  label: String,
  userInfo: String,
  updatingInfo: Boolean,
  error: String,
});

</script>

<template>
  <div class="profile-decoration-line"/>
  <div v-if="!updatingInfo" class="profile-field">
    <span class="profile-field-label">{{label}}</span>
    <span
        v-if="userInfo === '' || userInfo === null || userInfo === 0"
        class="profile-field-info-no-data"
    >
      There is no data available. Please update your personal information.
    </span>
    <span
        v-else
        class="profile-field-info"
    >
      {{userInfo}}
    </span>
  </div>
  <div v-else class="profile-field">
    <span class="profile-field-label">{{label}}</span>
    <input v-if="userInfo === '' || userInfo === null || userInfo === 0"
           class="updating-profile-input-field"
           @input="$emit('update:modelValue', $event.target.value)"
           @blur="$emit('blur')"/>
    <input v-else class="profile-field-info"/>
  </div>
</template>

<style scoped>

@import '@/assets/css/profile/user-profile.css';

</style>