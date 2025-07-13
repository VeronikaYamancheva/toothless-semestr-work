<script setup>

/* global defineProps */

import disabled from "@/assets/images/feature/disabled_fill_1.svg";
import info from "@/assets/images/feature/info_fill_1.svg";
import settings from "@/assets/images/feature/settings_fill_1.svg";
import toothlessSadIcon from "@/assets/images/toothless/toothless-sad.png";

const data = defineProps({
  id: String,
  name: String,
  price: Number,
  isAppointment: Boolean,
});

import { useRouter } from 'vue-router';
import { defineEmits, ref } from "vue";
import ProcedureDetailedModalWindow from "@/presentations/components/procedures/ProcedureDetailedModalWindow.vue";

const isDetailedWindowOpen = ref(false)
const modalWindowType = ref()
const modalWindowLabel = ref()
const emit = defineEmits(['procedureIdSelected', 'close']);


const openIndoWindow = () => {
  modalWindowType.value = 'read'
  modalWindowLabel.value = data.name
  isDetailedWindowOpen.value = true
}

const openDeleteWindow = () => {
  modalWindowLabel.value = 'Deleting procedure'
  modalWindowType.value = 'delete'
  isDetailedWindowOpen.value = true
}

const openSettingsWindow = () => {
  modalWindowLabel.value = 'Updating procedure'
  modalWindowType.value = 'update'
  isDetailedWindowOpen.value = true
}

const onClose = () => {
  isDetailedWindowOpen.value = false
  emit('close')
}

</script>

<template>

  <div class="procedures-decoration-line"/>
  <div
      class="procedures-block-item"
  >
    <img class="procedure-item-img" :src="toothlessSadIcon" alt="Toothless sad icon."/>
    <span class="procedure-item-title">{{ name }}</span>
    <span class="procedure-item-title">{{ price + "$"}}</span>
    <div class="management-block">
      <button
          class="management-button"
          @click="openIndoWindow"
      >
        <img
            class="management-image"
            :src="info"
            alt="Button info"
        />
      </button>
      <button
          class="management-button"
          @click="openSettingsWindow"
      >
        <img
            class="management-image"
            :src="settings"
            alt="Button settings"
        />
      </button>
      <button
          class="management-button"
          @click="openDeleteWindow"
      >
        <img
            class="management-image"
            :src="disabled"
            alt="Button disable"
        />
      </button>
    </div>
  </div>

  <div>
    <ProcedureDetailedModalWindow
        v-if="isDetailedWindowOpen"
        :procedure-id="id"
        :type="modalWindowType"
        :label="modalWindowLabel"
        @close="onClose()"
    />
  </div>

</template>

<style scoped>

@import "@/assets/css/procedures/procedures-style.css";

</style>