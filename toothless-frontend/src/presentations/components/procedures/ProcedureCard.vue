<script setup>

/* global defineProps */

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

const router = useRouter();
const isDetailedWindowOpen = ref(false)
const emit = defineEmits(['procedureIdSelected']);

const onClick = () => {

  if (data.isAppointment) {
    emit('procedureIdSelected', data.id);
  } else {
    openDetailedInfo();
  }
}

const openDetailedInfo = () => {
  isDetailedWindowOpen.value = true
}

</script>

<template>

  <div class="procedures-decoration-line"/>
  <div
      class="procedures-block-item selectable"
      @click="onClick"
  >
    <img class="procedure-item-img" :src="toothlessSadIcon" alt="Toothless sad icon."/>
    <span class="procedure-item-title">{{ name }}</span>
    <span class="procedure-item-title">{{ price + "$"}}</span>
  </div>

  <div>
    <ProcedureDetailedModalWindow
        v-if="isDetailedWindowOpen"
        :procedure-id="id"
        :type="`read`"
        :label="name"
        @close="isDetailedWindowOpen = false"
    />
  </div>

</template>

<style scoped>

@import "@/assets/css/procedures/procedures-style.css";

</style>