<script setup>

import ProcedureDetailedModalWindow from "@/presentations/components/procedures/ProcedureDetailedModalWindow.vue";

const props = defineProps({
  id: {
    type: String,
  },
  fullName: {
    type: String,
  },
  specialization: {
    type: String,
  },
  profilePhoto: {
    type: String
  },
  procedureLabel: {
    type: String,
  }
});

import { useRouter } from 'vue-router';
import {ref} from "vue";

const router = useRouter();
const isDetailedOpen = ref(false)

function goToDentistDetailedCard(id) {
  router.push(`/dentists/${props.id}`);
}

const openDetailedInfo = () => {
  isDetailedOpen.value = true
}

</script>

<template>

  <div v-if="specialization" class="home-page-scroll-card" @click="goToDentistDetailedCard(id)">
    <img class="home-page-scroll-card-img" :src="profilePhoto" alt="Dentist profile photo."/>

    <div class="home-page-scroll-card-text-block">
      <span class="home-page-scroll-card-text-label"> Full name:  </span>
      <span class="home-page-scroll-card-text"> {{fullName}}</span>
    </div>

    <div class="home-page-scroll-card-text-block">
      <span class="home-page-scroll-card-text-label"> Specialization:  </span>
      <span class="home-page-scroll-card-text">{{specialization}}</span>
    </div>

  </div>

  <div
      v-if="procedureLabel"
      class="home-page-scroll-card"
      @click="openDetailedInfo"
  >
    <img class="home-page-scroll-card-img" :src="profilePhoto" alt="Dentist profile photo."/>

    <div class="home-page-scroll-card-text-block">
      <span class="home-page-scroll-card-text-label"> Procedure:  </span>
      <span class="home-page-scroll-card-text">{{procedureLabel}}</span>
    </div>

  </div>

  <ProcedureDetailedModalWindow
      v-if="isDetailedOpen"
      :procedure-id="id"
      :type="`read`"
      :label="procedureLabel"
      @close="isDetailedOpen = false"
  />

</template>

<style scoped>

@import '@/assets/css/home/toothless-home-page.css';

</style>