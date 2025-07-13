<script setup>

import toothlessSadIcon from "@/assets/images/toothless/toothless-sad.png";
import ProcedureDetailedModalWindow from "@/presentations/components/procedures/ProcedureDetailedModalWindow.vue";

const props = defineProps({
  labelBlock: String
})
</script>

<template>

  <div class="profile-block">
    <span class="profile-title">{{ title }}</span>
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
  </div>

  <div class="profile-block">
    <span class="profile-title">{{ title }}</span>
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
  </div>
  <div class="profile-block">
    <span class="profile-title">{{ title }}</span>
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
  </div>
  <div class="profile-block">
    <span class="profile-title">{{ title }}</span>
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
  </div>


</template>

<style scoped>

</style>