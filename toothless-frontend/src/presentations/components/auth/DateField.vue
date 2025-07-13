<template>

  <div class="auth-text-field">
    <label class="auth-text-field-label" v-if="label">{{ label }}</label>

    <VueDatePicker
        class="auth-date-field-input"
        :enable-time="false"
        :format="'dd.MM.yyyy'"
        v-model="proxyValue"
        @input="$emit('update:modelValue', $event.target.value)"
        @blur="$emit('blur')"
    />

    <div class="auth-error-message" v-if="error" >{{ error }}</div>
  </div>
</template>

<script setup>

/* global defineProps */
/* global defineEmits */

import VueDatePicker from "@vuepic/vue-datepicker";
import {computed} from "vue";

const props = defineProps({
  modelValue: {
    type: [String, Date],
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

const proxyValue = computed({
  get: () => props.modelValue,
  set: (val) => emit('update:modelValue', val)
});
</script>

<style scoped>

@import "@/assets/css/auth/auth-input-field.css";

</style>