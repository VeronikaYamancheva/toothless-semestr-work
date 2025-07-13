<template>
  <AuthLayout
      :form-title="`Email verification`"
  >
    <form @submit.prevent="handleSubmit">
      <VerificationCodeInput
          v-model="code"
          label="Access code"
          :error="accessCodeError"
          @blur="accessCodeIsTouched = true"
          required
      />

      <SubmitButton :disabled="!isAccessCodeValid" :loading="loading" :label='`Verify email`'/>

    </form>
  </AuthLayout>
</template>

<script setup>
import {computed, ref} from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/store/auth.store';
import AuthLayout from '@/presentations/components/auth/AuthLayout.vue';
import SubmitButton from '@/presentations/components/auth/SubmitButton.vue';
import VerificationCodeInput from '@/presentations/components/auth/VerificationCodeInput.vue';
import { AuthService } from "@/services/auth.service";
import {verificationCodeRules} from "@/utils/validators/auth";

const router = useRouter();
const authStore = useAuthStore();

const code = ref('');
const loading = ref(false);
const error = ref(null);

const handleSubmit = async () => {
  try {
    loading.value = true;
    error.value = null;

    const response = await AuthService.verify(code.value, authStore.userId);

    await router.push('/login');
  } catch (err) {
    error.value = err.response?.data?.message || err.message || 'Ошибка при подтверждении кода';
  } finally {
    loading.value = false;
  }
};

const { touched: accessCodeIsTouched, errorRaw: accessCodeErrorRaw, error: accessCodeError } = useValidation(
    computed(() => code.value),
    verificationCodeRules
);

const isAccessCodeValid = computed(() =>
    !accessCodeErrorRaw.value);

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
</script>
