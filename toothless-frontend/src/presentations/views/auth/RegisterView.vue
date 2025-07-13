<template>
  <AuthLayout :form-title="`Registration form`">
    <UserTypeSwitch v-model="userType" />

    <form @submit.prevent="handleSubmit">
      <AuthInputField
          v-model="form.firstName"
          label="Name"
          :error="nameError"
          @blur="nameIsTouched = true"
      />

      <AuthInputField
          v-model="form.lastName"
          label="Last name"
          :error="lastNameError"
          @blur="lastNameIsTouched = true"
      />

      <AuthInputField
          v-model="form.middleName"
          label="Middle name"
          :error="middleNameError"
          @blur="middleNameIsTouched = true"
      />

      <AuthInputField
          v-model="form.email"
          label="Email"
          type="email"
          placeholder="example@example.ru"
          :error="emailCheckError"
          @blur="emailIsTouched = true"
      />

      <AuthInputField
          v-model="form.password"
          label="Password"
          type="password"
          :error="passwordError"
          @blur="passwordIsTouched = true"
      />

      <AuthInputField
          v-model="form.confirmPassword"
          label="Confirm password"
          type="password"
          :error="confirmPasswordError"
          @blur="confirmPasswordIsTouched = true"
      />

      <AuthInputField
          type="tel"
          v-model="form.phoneNumber"
          label="Phone number"
          placeholder="+7 (XXX) XXX-XX-XX"
          :error="phoneNumberError"
          @blur="phoneNumberIsTouched = true"
          @input="onInput"

      />

      <DateField
          v-model="form.birthDate"
          label="Date of birth"
          :error="birthDateError"
          @blur="birthDateIsTouched = true"
      />

      <VerificationCodeInput
          v-if="userType === 'dentist'"
          v-model="form.accessCode"
          label="Access Token"
          :error="verificationCodeError"
          @blur="verificationCodeIsTouched = true"
      />

      <div v-if="userType === 'client'">
        <SubmitButton
            :disabled="!isFormValid"
            :loading="loading"
            :label='`Complete registration`'
        />
      </div>

      <div v-else>
        <SubmitButton
            :disabled="!isDentistFormValid"
            :loading="loading"
            :label='`Complete registration`'
        />
      </div>

      <AuthLink
          to="/login"
      >
        Already have an account? Log in
      </AuthLink>

      <OAuthComponent v-if="userType === 'client'"></OAuthComponent>

    </form>
  </AuthLayout>
</template>

<script setup>
import { ref, watch, computed} from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/store/auth.store';
import AuthLayout from '@/presentations/components/auth/AuthLayout.vue';
import AuthInputField from '@/presentations/components/auth/AuthInputField.vue';
import DateField from '@/presentations/components/auth/DateField.vue';
import SubmitButton from '@/presentations/components/auth/SubmitButton.vue';
import AuthLink from '@/presentations/components/auth/AuthLink.vue';
import UserTypeSwitch from '@/presentations/components/auth/UserTypeSwitch.vue';
import VerificationCodeInput from '@/presentations/components/auth/VerificationCodeInput.vue';
import { emailRules, passwordRules, nameRules, confirmPasswordRules,
  phoneNumberRules, birthDateRules, verificationCodeRules, middleNameRules} from '@/utils/validators/auth';
import { AuthService } from '@/services/auth.service';
import OAuthComponent from "@/presentations/components/auth/OAuthComponent.vue";

const router = useRouter();
const authStore = useAuthStore();
const userType = ref('client');
const form = ref({
  firstName: '',
  lastName: '',
  middleName: '',
  email: '',
  password: '',
  confirmPassword: '',
  phoneNumber: '',
  birthDate: '',
  accessCode: ''
});

function useValidation(valueRef, rules, options = {}) {
  const touched = ref(false);
  const errorRaw = computed(() => {
    for (const rule of rules) {
      if (options.isOptionsExist) {
        if (valueRef.value !== options.passwordToConfirm.value) {
          return 'The confirmation password is entered incorrectly'
        }

      }
      const result = rule(valueRef.value);
      if (result !== true) return result;
    }
    return '';
  });
  const error = computed(() => (touched.value ? errorRaw.value : ''));
  return { touched, errorRaw, error };
}

const { touched: nameIsTouched, errorRaw: nameErrorRaw, error: nameError } = useValidation(
    computed(() => form.value.firstName),
    nameRules
);
const { touched: lastNameIsTouched, errorRaw: lastNameErrorRaw, error: lastNameError } = useValidation(
    computed(() => form.value.lastName),
    nameRules
);
const { touched: middleNameIsTouched, errorRaw: middleNameErrorRaw, error: middleNameError } = useValidation(
    computed(() => form.value.middleName),
    middleNameRules
);
const { touched: emailIsTouched, errorRaw: emailErrorRaw, error: emailError } = useValidation(
    computed(() => form.value.email),
    emailRules
);
const { touched: passwordIsTouched, errorRaw: passwordErrorRaw, error: passwordError } = useValidation(
    computed(() => form.value.password),
    passwordRules
);
const { touched: confirmPasswordIsTouched, errorRaw: confirmPasswordErrorRaw, error: confirmPasswordError } = useValidation(
    computed(() => form.value.confirmPassword),
    confirmPasswordRules,
    {
      isOptionsExist: true,
      passwordToConfirm: computed( () => form.value.password),
    }
);
const { touched: phoneNumberIsTouched, errorRaw: phoneNumberErrorRaw, error: phoneNumberError } = useValidation(
    computed(() => form.value.phoneNumber),
    phoneNumberRules
);
const { touched: birthDateIsTouched, errorRaw: birthDateErrorRaw, error: birthDateError } = useValidation(
    computed(() => form.value.birthDate),
    birthDateRules
);
const { touched: verificationCodeIsTouched, errorRaw: verificationCodeErrorRaw, error: verificationCodeError } = useValidation(
    computed(() => form.value.accessCode),
    verificationCodeRules
);


const isFormValid = computed(() =>
    !nameErrorRaw.value &&
    !lastNameErrorRaw.value  &&
    !middleNameErrorRaw.value  &&
    !emailErrorRaw.value &&
    !passwordErrorRaw.value &&
    !confirmPasswordErrorRaw.value &&
    !phoneNumberErrorRaw.value &&
    !birthDateErrorRaw.value
);

const isDentistFormValid = computed(() =>
isFormValid.value && !verificationCodeErrorRaw.value)


const loading = ref(false);
const error = ref(null);
const errorCheckingEmail = ref(null);
const emailAvailable = ref(true);
const emailCheckError = computed ( () => {
  return emailError.value || errorCheckingEmail.value;
})

const checkEmail = async () => {
  if (!emailErrorRaw.value) {
    try {

      const response = await AuthService.checkEmailAvailability(form.value.email);
      emailAvailable.value = response;

      if (!response.isAvailable) {
        errorCheckingEmail.value = 'This email is already registered.';
      } else {
        errorCheckingEmail.value = null;
      }
    } catch (err) {
      errorCheckingEmail.value = err.value;
    }
  }
};

watch(() => form.value.email, checkEmail);

const handleSubmit = async () => {

  console.log(form.value.birthDate,)

  nameIsTouched.value = true;
  lastNameIsTouched.value = true;
  middleNameIsTouched.value = true;
  emailIsTouched.value = true;
  passwordIsTouched.value = true;
  confirmPasswordIsTouched.value = true;
  phoneNumberIsTouched.value = true;
  birthDateIsTouched.value = true;
  if (isFormValid.value) {

    console.log(form.value)
    try {
      loading.value = true;
      error.value = null;

      if (!emailAvailable.value) {
        throw new Error('Этот email уже занят');
      }

      const userData = {
        firstName: form.value.firstName,
        lastName: form.value.lastName,
        middleName: form.value.middleName || null,
        email: form.value.email,
        password: form.value.password,
        confirmPassword: form.value.confirmPassword,
        phoneNumber: form.value.phoneNumber,
        birthDate: form.value.birthDate,
        ...(userType.value === 'dentist' && { accessCode: form.value.accessCode })
      };
      const response = userType.value === 'client'
          ? await AuthService.registerClient(userData)
          : await AuthService.registerDentist(userData);

      authStore.register(response);

      router.push('/verify');

    } catch (err) {
      error.value = err.response?.data?.message || err.message || 'Ошибка при регистрации';
    } finally {
      loading.value = false;
    }
  }
};

/* global defineProps */
/* global defineEmits */

const props = defineProps({
  modelValue: String
});
const emit = defineEmits(['update:modelValue']);

watch(() => props.modelValue, (newVal) => {
  if (newVal !== form.value.phoneNumber) {
    form.value.phoneNumber = newVal;
  }
});

function onInput(e) {
  let number = e.target.value.replace(/\D/g, '');
  if (number.startsWith('8')) number = '7' + number.slice(1);
  if (!number.startsWith('7')) number = '7' + number;

  number = number.substring(0, 11);

  let formatted = '+7';
  if (number.length > 1) formatted += ' (' + number.slice(1, 4);
  if (number.length >= 4) formatted += ') ' + number.slice(4, 7);
  if (number.length >= 7) formatted += '-' + number.slice(7, 9);
  if (number.length >= 9) formatted += '-' + number.slice(9, 11);

  form.value.phoneNumber = formatted;
  emit('update:modelValue', form.value.phoneNumber);
}

</script>