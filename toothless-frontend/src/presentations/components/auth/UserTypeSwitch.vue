<template>
  <div class="user-type-switch">
    <button
        v-for="option in options"
        :key="option.value"
        @click="$emit('update:modelValue', option.value)"
        :class="{ active: modelValue === option.value }"
    >
      {{ option.label }}
    </button>
  </div>
</template>

<script setup>
/* global defineProps */
/* global defineEmits */

defineProps({
  modelValue: {
    type: String,
    required: true
  }
});

defineEmits(['update:modelValue']);

const options = [
  { value: 'client', label: 'Client' },
  { value: 'dentist', label: 'Dentist' }
];
</script>

<style scoped>

@import "@/assets/css/auth/auth-user-type-switch.css";

</style>