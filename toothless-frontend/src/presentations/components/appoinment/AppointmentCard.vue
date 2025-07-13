<script setup>

/* global defineProps */

import toothlessInMask from "@/assets/images/toothless/toothless-in-mask-2.png";

const data = defineProps({
  appointmentId: String,
  client: String,
  dentist: String,
  date: String,
  beginTime: String,
  endTime: String,
});

import {computed, defineEmits, onMounted, ref} from "vue";
import AppointmentDetailedModalWindow from "@/presentations/components/appoinment/AppointmentDetailedModalWindow.vue";

const isDetailedWindowOpen = ref(false)
const emit = defineEmits(['procedureIdSelected']);

const clientName = computed( () => {
  return getFullName(data.client.firstName, data.client.middleName, data.client.lastName)
});

const dentistName = computed( () => {
  return getFullName(data.dentist.firstName, data.dentist.middleName, data.dentist.lastName)
});

const dateOfAppointment = computed( () => {
  return getDateAtFormat(data.date)
})

const timeOfAppointment = computed( () => {
  return getTimeAtFormat(data.beginTime, data.endTime)
})

onMounted( () => {

  console.log("client", data.client)
  console.log("dentist", data.dentist)

  console.log('data.date.value', getDateAtFormat(data.date))
  console.log('data.beginTime', data.beginTime)
  console.log('data.endTime', data.endTime)


  console.log('data.date.value', getTimeAtFormat(data.beginTime, data.endTime))


  for(const field in data.client) {
    console.log(field)
  }
})

const getFullName = (first, middle, last) => {
  return `${first} ${middle ? middle + ' ' : ''}${last}`
}

const getDateAtFormat = (date) => {
  if (!date) return ''; // или можно вернуть значение по умолчанию
  const [year, month, day] = date.split('-');
  return `${day}.${month}.${year}`;
}

const getTimeAtFormat = (beginTime, endTime) => {
  if (!beginTime || !endTime) return ''; // защита от undefined
  const [hour, minute] = beginTime.split(':');
  const [hh, mm] = endTime.split(':');
  return `${hour}:${minute}-${hh}:${mm}`;
}

</script>

<template>

  <div class="procedures-decoration-line"/>
  <div
      class="procedures-block-item selectable"
      @click="isDetailedWindowOpen = true"
  >
    <img class="procedure-item-img" :src="toothlessInMask" alt="Toothless sad icon."/>
    <span class="procedure-item-title">{{ clientName }}</span>
    <span class="procedure-item-title">{{ dentistName }}</span>
    <span class="procedure-item-title">{{ dateOfAppointment }}</span>
    <span class="procedure-item-title">{{ timeOfAppointment }}</span>
  </div>

  <div>
    <AppointmentDetailedModalWindow
        v-if="isDetailedWindowOpen"
        :appointment-id="data.appointmentId"
        :label="`Appointment`"
        @close="isDetailedWindowOpen = false"
    />
  </div>

</template>

<style scoped>

@import "@/assets/css/procedures/procedures-style.css";

</style>