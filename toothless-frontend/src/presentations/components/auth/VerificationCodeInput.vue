<template>
  <div class="auth-text-field">
    <label class="auth-text-field-label" v-if="label">{{ label }}</label>
    <div class="code-inputs">
      <input
          v-for="(digit, index) in digits"
          :key="index"
          v-model="digits[index]"
          @input="handleInput(index, $event)"
          @keydown.delete="handleNavigation(index, 'left', $event)"
          @keydown.right="handleNavigation(index, 'right', $event)"
          @keydown.left="handleNavigation(index, 'left', $event)"
          maxlength="1"
          type="text"
          inputmode="numeric"
          pattern="[0-9]*"
          @blur="$emit('blur')"
      >
    </div>
    <div class="auth-error-message" v-if="error" >{{ error }}</div>
  </div>
</template>

<script setup>
/* global defineProps */
/* global defineEmits */

import { ref, watch } from 'vue';

const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  },
  label: {
    type: String,
    default: ''
  },
  required: {
    type: Boolean,
    default: false
  },
  error: {
    type: String,
    default: ''
  }
});

const emit = defineEmits(['update:modelValue']);

const digits = ref(['', '', '', '', '', '']);

watch(() => props.modelValue, (newVal) => {
  if (newVal.length === 6) {
    digits.value = newVal.split('');
  }
});

watch(digits, (newDigits) => {
  emit('update:modelValue', newDigits.join(''));
}, { deep: true });

const handleInput = (index, event) => {
  const value = event.target.value;
  if (value && index < 5) {
    event.target.nextElementSibling?.focus();
  }
};

const handleNavigation = (index, direction, event) => {
  const isEmpty = !digits.value[index];
  if (!isEmpty) return;

  const sibling =
      direction === 'left' && index > 0
          ? event.target.previousElementSibling
          : direction === 'right' && index < digits.value.length - 1
              ? event.target.nextElementSibling
              : null;

  sibling?.focus();
  sibling?.select();
};

</script>

<style scoped>

@import "@/assets/css/auth/auth-input-field.css";

</style>