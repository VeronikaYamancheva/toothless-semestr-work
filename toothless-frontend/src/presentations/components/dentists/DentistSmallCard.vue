<script setup>

/* global defineProps */

import toothlessSadIcon from "@/assets/images/toothless/toothless-sad.png";

const data = defineProps({
  id: String,
  name: String,
  specialization: String,
});

import {defineEmits} from "vue";

const emit = defineEmits(['dentistIdSelected']);

</script>

<template>

  <div class="procedures-decoration-line"/>
  <div
      class="procedures-block-item selectable"
      @click="emit('dentistIdSelected', data.id)"
  >
    <img class="procedure-item-img" :src="toothlessSadIcon" alt="Toothless sad icon."/>
    <span class="procedure-item-title">{{ name }}</span>
    <span class="procedure-item-title">{{specialization}}</span>
  </div>

</template>

<style scoped>

@import '@/assets/css/home/toothless-home-page.css';

</style>