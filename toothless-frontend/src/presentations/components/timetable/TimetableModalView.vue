<script setup>

/*global defineProps*/
/*global defineEmits*/

import {AppointmentService} from "@/services/appointment.service";

const emit = defineEmits(["close"]);

const data = defineProps({
  clientName: String,
  dentistId: String,
  dentistName: String,
  dentistSpecialization: String,
  procedureId: String,
  procedureName: String,
  date: String,
  time: String,
  isPreview: Boolean
});

const timeIntervalConverter = (timeInterval) => {
  const [beginTime, endTime] = timeInterval.split("-")
  return [`${beginTime}:00`, `${endTime}:00`]
}

const dateConverter = (date) => {
  const [dd, MM, yyyy] = date.split(".")
  return `${yyyy}-${MM}-${dd}`
}

const createAppointment = async () => {
  try {

    const [beginTime, endTime] = timeIntervalConverter(data.time);

    const formData = new FormData();

    formData.append("dentistId", data.dentistId);
    formData.append("beginTime", beginTime);
    formData.append("endTime", endTime);
    formData.append("date", dateConverter(data.date));
    formData.append("procedureId", data.procedureId);

    await AppointmentService.createAppointment(formData)
    emit('close')
  } catch (error) {
    console.error("Error at createAppointment: " + error);

  }
}

</script>

<template>

  <div class="timetable-modal-window-overlay" @click.self="emit('close')">
      <div class="timetable-modal-window-field">
        <div class="timetable-modal-window-item-right">
          <button class="timetable-modal-window-close-button" @click="emit('close')">×</button>
        </div>

        <div class="timetable-modal-window-field-title">
          <span>Confirm the appointment</span>
        </div>

        <form
            @submit.prevent="createAppointment()"
            class="timetable-modal-window-item-left"
        >
          <div>
            <span class="timetable-modal-field-label">
              {{data.clientName}}
            </span>

            <div class="timetable-modal-window-text-description">
              <span>Makes an appointment with the dentist</span>
            </div>

            <div>
              <span class="timetable-modal-field-label">Dentist name: </span>
              {{dentistName}}
            </div>

            <div>
              <span class="timetable-modal-field-label">Dentist specialization: </span>
              {{dentistSpecialization}}
            </div>

            <div
                v-if="procedureName"
            >
              <span class="timetable-modal-field-label">Procedure name: </span>
              {{procedureName}}
            </div>

            <div>
              <span class="timetable-modal-field-label">Date: </span>
              {{date}}
            </div>

            <div>
              <span class="timetable-modal-field-label">Time: </span>
              {{time}}
            </div>
          </div>

          <div class="timetable-modal-window-item-center timetable-modal-window-text-description">
            <span >*if you don't want create appointment, just leave it blank</span>
          </div>

          <div class="timetable-modal-window-item-center">
            <button
                type={{create}}
                class="timetable-modal-window-field-button"
            >
              Create
            </button>
            <button
                class="timetable-modal-window-field-button"
                @click="emit('close')"
            >
              Cancel
            </button>
          </div>

        </form>

      </div>
  </div>

</template>

<style scoped>

@import "@/assets/css/timetable/timetable-modal.css";

</style>