<template>
  <AuthLayout :form-title="`Login form`">

    <form @submit.prevent="handleSubmit">
      <AuthInputField
          v-model="form.email"
          label="Email"
          type="email"
          :error="emailError"
          @blur="emailTouched = true"
      />

      <AuthInputField
          v-model="form.password"
          label="Password"
          type="password"
          :error="passwordError"
          @blur="passwordTouched = true"
      />

      <SubmitButton :disabled="!isFormValid" :loading="loading" :label='`Log in`'/>

      <AuthLink to="/register">
        New user? Create an account
      </AuthLink>

      <OAuthComponent/>

    </form>
  </AuthLayout>
</template>

<script setup>
import {ref, computed, inject} from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from "@/store/auth.store";
import AuthLayout from '@/presentations/components/auth/AuthLayout.vue';
import AuthInputField from '@/presentations/components/auth/AuthInputField.vue';
import SubmitButton from '@/presentations/components/auth/SubmitButton.vue';
import AuthLink from '@/presentations/components/auth/AuthLink.vue';
import { emailRules, confirmPasswordRules } from '@/utils/validators/auth';
import { AuthService } from '@/services/auth.service';
import OAuthComponent from "@/presentations/components/auth/OAuthComponent.vue";

const router = useRouter();
const authStore = useAuthStore();
const form = ref({email: '', password: ''});
const loading = ref(false);
const error = ref(null);

function useValidation(valueRef, rules) {
  const touched = ref(false);
  const errorRaw = computed(() => {
    for (const rule of rules) {
      const result = rule(valueRef.value);
      if (result !== true) return result;
    }
    return '';
  });
  const error = computed(() => (touched.value ? errorRaw.value : ''));
  return { touched, errorRaw, error };
}

const { touched: emailTouched, errorRaw: emailErrorRaw, error: emailError } = useValidation(
    computed(() => form.value.email),
    emailRules
);
const { touched: passwordTouched, errorRaw: passwordErrorRaw, error: passwordError } = useValidation(
    computed(() => form.value.password),
    confirmPasswordRules
);

const isFormValid = computed(() => !emailErrorRaw.value && !passwordErrorRaw.value);

const handleSubmit = async () => {
  emailTouched.value = true
  passwordTouched.value = true
  if (isFormValid.value) {
    try {
      loading.value = true;
      error.value = null;

      const response = await AuthService.login(form.value);

      authStore.login({
        accessToken: response.accessToken
      });

      await router.push('/profile');
    } catch (err) {
      const message = err.response?.data?.message || 'Authorization error';

      if (err.response?.status === 403 && message === 'Email is not verified') {
        router.push('/verify'); // редиректим на верификацию
      } else {
        error.value = message;
        console.error('Login error:', err);

      }
    } finally {
      loading.value = false;
    }
  }
};
</script>