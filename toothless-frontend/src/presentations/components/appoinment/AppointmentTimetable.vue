<script setup>

import {onMounted, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import {TimetableService} from "@/services/timetable.service";
import {ProcedureService} from "@/services/procedure.service";
import {DentistsService} from "@/services/dentists.service";
import TimetableComponent from "@/presentations/components/timetable/TimetableComponent.vue";

const route = useRoute();
const procedureId = route.params.procedureId;
const procedure = ref();
const dentistId = route.params.dentistId;
const dentist = ref();

onMounted( async () => {
  try {

    procedure.value = await ProcedureService.getProcedureById(procedureId)
    dentist.value = await DentistsService.getDentistById(dentistId)
  }
  catch(error) {
    console.log("Error at AppointmentTimetable on mounted: " + error);
  }
});

</script>

<template>
  <span class="dentist-block-main-info-text">Selected procedure: {{procedure?.name}} <br></span>
  <span class="dentist-block-main-info-text">Selected dentist: {{dentist?.firstName + ' ' + dentist?.middleName + ' ' + dentist?.lastName}}<br></span>

  <div class="opt">
    <TimetableComponent
        :dentist-id="dentistId"
        :dentist-name="dentist?.firstName + ' ' + dentist?.middleName + ' ' + dentist?.lastName"
        :dentist-specialization="dentist?.specialization"
        :procedure-id="procedureId"
        :procedure-name="procedure?.name"
    />
  </div>

</template>

<style scoped>

</style>