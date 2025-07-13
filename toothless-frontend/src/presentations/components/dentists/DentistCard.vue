<script setup>

/* global defineProps */

defineProps({
  id: {
    type: Number,
  },
  fullName: {
    type: String,
  },
  specialization: {
    type: String,
  },
  profilePhoto: {
    type: String
  }
});

import { useRouter } from 'vue-router';

const router = useRouter();

function goToDentistDetailedCard(id) {
  router.push(`/dentists/${id}`);
}

</script>

<template>

  <div class="dentists-card" @click="goToDentistDetailedCard(id)">
    <div>
      <img class="dentists-card-img" :src="profilePhoto" alt="Dentist profile photo."/>
    </div>
    <div>
      <div class="dentists-card-text-block">
        <span class="dentists-card-text-label">{{ `Full name:` }} </span>
        <span class="dentists-card-text"> {{fullName}}</span>
      </div>
      <div class="dentists-card-text-block">
        <span class="dentists-card-text-label"> {{`Specialization: `}}  </span>
        <span class="dentists-card-text">{{specialization}}</span>
      </div>
    </div>


  </div>


</template>

<style scoped>

@import '@/assets/css/dentists/dentists-page.css';

</style>