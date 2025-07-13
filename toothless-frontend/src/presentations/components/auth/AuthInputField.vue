<template>
  <div class="auth-text-field">
    <label class="auth-text-field-label" v-if="label">{{ label }}</label>
    <input class="auth-text-field-input"
        :value="modelValue"
        @input="$emit('update:modelValue', $event.target.value)"
        @blur="$emit('blur')"
        :type="type"
        :placeholder="placeholder"
    >
    <div v-if="error" class="auth-error-message">{{ error }}</div>
  </div>
</template>

<script setup>
/* global defineProps */
/* global defineEmits */

defineProps({
  modelValue: {
    type: [String, Number],
    default: ''
  },
  label: {
    type: String,
    default: ''
  },
  type: {
    type: String,
    default: 'text'
  },
  placeholder: {
    type: String,
    default: ''
  },
  error: {
    type: String,
    default: ''
  }
});

defineEmits(['update:modelValue'], 'blur');

</script>

<style scoped>

@import "@/assets/css/auth/auth-input-field.css";

</style>