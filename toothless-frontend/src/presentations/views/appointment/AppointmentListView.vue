<script setup>
import { ref, computed, onMounted } from 'vue';
import { ProcedureService } from "@/services/procedure.service";
import update from "@/assets/images/feature/update_fill_0.svg";
import VueDatePicker from "@vuepic/vue-datepicker";
import AppointmentCard from "@/presentations/components/appoinment/AppointmentCard.vue";
import {AppointmentService} from "@/services/appointment.service";

/* global defineProps */
/* global defineEmits */

defineProps({
  isAppointment: Boolean,
  blockLabel: String,
});

const appointments = ref();
const startDate = ref()
const endDate = ref()

const updateAppointments = async () => {

  if (startDate.value) {
    startDate.value = new Date(startDate.value)

    startDate.value = `${startDate.value.getFullYear()}-${startDate.value.getMonth() + 1}-${startDate.value.getDate()}`;
  }
  else {
    startDate.value = null
  }

  if (endDate.value) {
    endDate.value = new Date(endDate.value)
    endDate.value = `${endDate.value.getFullYear()}-${endDate.value.getMonth() + 1}-${endDate.value.getDate()}`;

  } else  {

    endDate.value = null
  }

  const AppointmentServiceResponse = await AppointmentService.getAppointmentsByDateRange(getFormatDate(startDate.value), getFormatDate(endDate.value))

  appointments.value = AppointmentServiceResponse;

}

const getFormatDate = (date) => {
  if (!date) return ''; // или можно вернуть значение по умолчанию
  const [year, month, day] = date.split('-');
  return `${year}-${month.length > 1 ? month : '0' + month}-${day.length > 1 ? day : '0' + day}`;
}

onMounted( async () => {
  console.log("Start onMounted")
  await updateAppointments()
  console.log("End onMounted")
});

</script>

<template>

  <div class="procedures-container">

    <div class="procedures-title-h1">
      <span>APPOINTMENT LIST</span>
    </div>

    <div class="management-add-field">
      <div class="management-date">

        <div class="management-date-item">
          <VueDatePicker
              class="auth-date-field-input"
              :enable-time="false"
              :format="'dd.MM.yyyy'"
              v-model="startDate"
          />
        </div>
        <div class="management-date-item">
          <VueDatePicker
              class="auth-date-field-input"
              :enable-time="false"
              :format="'dd.MM.yyyy'"
              v-model="endDate"
          />
      </div>

      </div>
      <div class="management-add-field">
        <div class="management-add-item">
          <button
              @click="updateAppointments()"
              class="management-add-button"
          >
            <img class="management-add-button-img" :src="update" alt="add_box">
            <span class="management-add-button-text">Update List</span>
          </button>

        </div>
      </div>
    </div>

    <div
        class="procedures-block"
    >

      <div class="procedures-block-item">
        <div class="procedures-block-item-content" >
          <span class="procedures-block-item-labels">Client icon</span>
        </div>
        <div class="procedures-block-item-content">
          <span class="procedures-block-item-labels">Client name</span>
        </div>
        <div class="procedures-block-item-content">
          <span class="procedures-block-item-labels">Dentist name</span>
        </div>
        <div class="procedures-block-item-content">
          <span class="procedures-block-item-labels">Date</span>
        </div>
        <div class="procedures-block-item-content">
          <span class="procedures-block-item-labels">Time</span>
        </div>
      </div>

      <div>
        <div
            v-for="appointment in appointments"
            :key="appointment.appointmentId"
        >
          <AppointmentCard
              :appointment-id="appointment.appointmentId"
              :client="appointment.client"
              :dentist="appointment.dentist"
              :date="appointment.date"
              :begin-time="appointment.beginTime"
              :end-time="appointment.endTime"
              @close=""
          />
        </div>
        <div class="procedures-decoration-line"/>

      </div>
    </div>
  </div>

</template>

<style scoped>

@import "@/assets/css/procedures/procedures-style.css";

</style>