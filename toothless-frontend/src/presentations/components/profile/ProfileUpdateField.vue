<script setup>
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'
import { ref, watch, computed } from 'vue';

const props = defineProps({
  label: String,
  modelValue: String,
  rules: Array,
  error: String
});

const emit = defineEmits(['update:modelValue']);
const inputValue = ref(props.modelValue || '');
const error = ref(null);


watch(inputValue, () => {
  if (error.value) {
    validate();
  }
});

const validate = () => {
  error.value = null;
  for (const rule of props.rules || []) {
    const result = rule(inputValue.value);
    if (result !== true && result !== '') {
      error.value = result;
      return false;
    }
  }
  return true;
};

const computedError = computed(() => {
  return error.value || props.error || null;
});

const rawPhone = ref(props.modelValue || '');

const formattedPhone = computed(() => {
  let number = rawPhone.value.replace(/\D/g, '');
  if (number.startsWith('8')) number = '7' + number.slice(1);
  if (!number.startsWith('7')) number = '7' + number;
  number = number.substring(0, 11);

  let formatted = '+7';
  if (number.length > 1) formatted += ' (' + number.slice(1, 4);
  if (number.length >= 4) formatted += ') ' + number.slice(4, 7);
  if (number.length >= 7) formatted += '-' + number.slice(7, 9);
  if (number.length >= 9) formatted += '-' + number.slice(9, 11);

  return formatted;
});

const onPhoneInput = (e) => {
  const digits = e.target.value.replace(/\D/g, '');
  rawPhone.value = digits;
  emit('update:modelValue', formattedPhone.value);
};

const seePassword = (id) => {
  var x = document.getElementById(id);
  if (x.type === "password") {
    x.type = "text";
  } else {
    x.type = "password";
  }
}

const handleBlur = () => {
  if (!inputValue.value || typeof inputValue.value !== 'string') return;
  if (!inputValue.value.trim()) return;
  if (validate()) {
    emit('update:modelValue', inputValue.value);
  }
};
</script>

<template>
  <div class="profile-decoration-line" />
  <div class="update-profile-field">
    <span class="update-profile-field-label">{{ label }}</span>
    <div class="update-profile-decoration-line-vertical" />
    <div class="update-profile-field-info">

      <input
          v-if="label === 'Date of birth'"
          class="update-profile-input-field"
          type="text"
          v-model="inputValue"
          @blur="handleBlur"
      />

      <input
          v-else-if="label === 'Phone number'"
          class="update-profile-input-field"
          type="text"
          v-model="formattedPhone"
          @input="onPhoneInput"
          @blur="handleBlur"
      />

      <input
          v-else
          class="update-profile-input-field"
          type="text"
          v-model="inputValue"
          @blur="handleBlur"
      />

      <div v-if="computedError" class="update-profile-error-message">{{ computedError }}</div>
    </div>
  </div>
</template>

<style scoped>

@import '@/assets/css/profile/update-profile-modal-window.css';

</style>