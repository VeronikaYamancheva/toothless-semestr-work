<script setup>

/*global defineProps*/

import {computed, ref} from 'vue'
import ProfileUpdateField from "@/presentations/components/profile/ProfileUpdateField.vue";
import {ProfileService} from "@/services/profile.service";

const emit = defineEmits(['updated']);

const props = defineProps({
  type: String,
  data: Object
});

const initialData = props.data;

const error = ref(null);
const isModalWindowShow = ref(false)
const newData = ref();
const fieldStates = ref({});

const showModalWindow = () => {
  newData.value = {};

  for (const key in initialData) {
    const field = initialData[key];
    const val = key === "birthDate" ? dateOfBirthToISO(field.value) : field.value;

    newData.value[key] = {
      label: field.label,
      value: val,
      isChangeable: field.isChangeable,
      rules: field.rules
    };

    console.log("point 1")
    if (field.rules?.length) {
      const { touched, errorRaw, error } = useValidation(
          computed(() => newData.value[key].value),
          field.rules
      );
      fieldStates.value[key] = { touched, errorRaw, error };
    }
  }
  openModalWindow()
}

const openModalWindow = () => {
  isModalWindowShow.value = true
}

const closeModalWindow = () => {
  isModalWindowShow.value = false
}

function dateOfBirthToISO(value) {
  if (value !== null) {
    const [day, month, year] = value.split('.')
    return `${year}-${month}-${day}`}
}

const updateProfileInfo = async () => {

  for (const key in fieldStates.value) {
    fieldStates.value[key].touched = true;
  }

  const hasErrors = Object.values(fieldStates.value).some(
      (f) => f.errorRaw.value
  );
  if (hasErrors) return;

  try {
    error.value = null;

    for (const key in newData.value) {
      if (!initialData[key]) initialData[key] = {};
      initialData[key].value = newData.value[key].value;
    }

    const updatedData = {};
    for (const [key, field] of Object.entries(initialData)) {
      if (field.value && field.isChangeable) {
        updatedData[key] = field.value;
      }
    }

    switch (props.type) {
      case "base":
        await ProfileService.updateBaseProfile(updatedData);
        break;
      case "client":
        await ProfileService.updateClientProfile(updatedData);
        break;
      case "dentist":
        await ProfileService.updateDentistProfile(updatedData);
        break;
      case "password":
        await ProfileService.updatePassword(updatedData);
        break;
    }
    emit('updated')
    closeModalWindow()
  } catch (err) {
    console.log("error: " + err)
  }
};

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

<template>
  <div class="profile-decoration-line"/>
  <div class="profile-update-field">

    <button class="update-profile-information-button" @click="showModalWindow">
      <span class="update-profile-information-button-label">Update profile information</span>
    </button>

    <div
        v-if="isModalWindowShow"
        class="update-profile-modal-window-overlay"
        @click.self="closeModalWindow"
    >
      <div class="update-profile-modal-window-field">
        <div class="update-profile-modal-window-item-right">
          <button
              class="update-profile-modal-window-close-button"
              @click="closeModalWindow"
          >
            ×
          </button>
        </div>

        <div class="update-profile-modal-window-field-title">
          <span>
            Enter new information
          </span>
        </div>

        <form @submit.prevent="updateProfileInfo">
          <div
              v-for="item in newData"
              :key="item.label"
          >
                <ProfileUpdateField
                    v-if="item.isChangeable"
                    :label="item.label"
                    v-model="item.value"
                    :rules="item.rules"
                    :error="fieldStates[item.label]?.error"
                />

          </div>
          <div class="profile-decoration-line"></div>

          <div  class="update-profile-modal-window-text-description">
            <span
                v-if="props.type !== 'base' && props.type !== 'password'"
            >
              *if you don't want to change info in the field, just leave it blank</span>
          </div>

          <div class="update-profile-modal-window-item-center">
            <button
                :disabled="error"
                type="submit"
                class="update-profile-modal-window-field-button"
            >
              Update info</button>
            <button class="update-profile-modal-window-field-button" @click="closeModalWindow">Leave it unchanged</button>
          </div>

        </form>

      </div>
    </div>
  </div>
</template>

<style scoped>

@import "@/assets/css/profile/update-profile-modal-window.css";

</style>