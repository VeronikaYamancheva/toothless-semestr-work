<script setup>

import { onMounted, ref} from "vue";
import toothlessDentist from "@/assets/images/toothless/toothless-dentist.png";
import {AppointmentService} from "@/services/appointment.service";

const props = defineProps({
  appointmentId: String,
  label: String
})

const emit = defineEmits(["close"]);

const modalWindowLabel = ref()
const appointment = ref()
const appointmentClient = ref()
const appointmentDentist = ref()
const clientName = ref()
const dentistName = ref()

onMounted( async () => {
  try {
    if (props.appointmentId) {

      appointment.value = await AppointmentService.getAppointmentById(props.appointmentId);
      appointmentClient.value = appointment.value.client
      appointmentDentist.value = appointment.value.dentist
      clientName.value = getFullName(appointmentClient.value.firstName, appointmentClient.value.middleName, appointmentClient.value.lastName);
      dentistName.value = getFullName(appointmentDentist.value.firstName, appointmentDentist.value.middleName, appointmentDentist.value.lastName);
      modalWindowLabel.value = props.label
    }
  }
  catch (error) {
    console.log("Error at procedureDetailed:" + error)
  }
});

const getFullName = (first, middle, last) => {
  return `${first} ${middle ? middle + ' ' : ''}${last}`
}

const getDateAtFormat = (date) => {
  if (!date) return '';
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

  <div
      class="procedure-modal-window-overlay"
      @click.self="emit('close')"
  >

    <div class="procedure-modal-window-field small">

      <div class="procedure-modal-window-item-right">

        <button
            class="procedure-modal-window-close-button"
            @click="emit('close')"
        >
          ×
        </button>

      </div>

      <div class="procedure-modal-window-field-title">
        <span>{{ modalWindowLabel }}</span>
      </div>
      <div class="procedure-modal-window-item-center">
        <img class="procedure-modal-window-img" :src="toothlessDentist" alt="Procedure icon"/>
      </div>

      <div
          class="procedure-modal-window-item-left"
      >
        <div>

          <div
              class="procedure-modal-field-text"
          >
            <span class="procedure-modal-field-label">Client name: </span>
            {{ clientName }}
          </div>

          <div
              class="procedure-modal-field-text"
          >
            <span class="procedure-modal-field-label">Client email: </span>
            {{ appointmentClient?.email }}
          </div>

          <div
              class="procedure-modal-field-text"
          >
            <span class="procedure-modal-field-label">Client phone: </span>
            {{ appointmentClient?.phoneNumber }}
          </div>

          <div
              class="procedure-modal-field-text"
          >
            <span class="procedure-modal-field-label">Client birth date: </span>
            {{ appointmentClient?.birthDate }}
          </div>

          <div
              class="procedure-modal-field-text"
          >
            <span class="procedure-modal-field-label">Dentist name: </span>
            {{ dentistName }}
          </div>

          <div
              class="procedure-modal-field-text"
          >
            <span class="procedure-modal-field-label">Dentist email: </span>
            {{ appointmentDentist?.email }}
          </div>

          <div
              class="procedure-modal-field-text"
          >
            <span class="procedure-modal-field-label">Dentist phone: </span>
            {{ appointmentDentist?.phoneNumber }}
          </div>

          <div
              class="procedure-modal-field-text"
          >
            <span class="procedure-modal-field-label">Appointment date: </span>
            {{ getDateAtFormat(appointment?.date) }}
          </div>

          <div
              class="procedure-modal-field-text"
          >
            <span class="procedure-modal-field-label">Appointment time: </span>
            {{ getTimeAtFormat(appointment?.beginTime, appointment?.endTime) }}
          </div>

        </div>
      </div>
    </div>
  </div>

</template>

<style scoped>

@import "@/assets/css/procedures/procedure-detailed-window.css";

</style>