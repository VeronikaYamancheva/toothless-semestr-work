<script setup>

import {useRoute, useRouter} from "vue-router";
import {onMounted, ref, watch} from "vue";
import {DentistsService} from "@/services/dentists.service";
import ProcedureCard from "@/presentations/components/procedures/ProcedureCard.vue";
import DentistSmallCard from "@/presentations/components/dentists/DentistSmallCard.vue";
import {ProcedureService} from "@/services/procedure.service";

const route = useRoute();
const router = useRouter();
const procedureId = route.params.procedureId;
const procedure = ref();

const dentists = ref([])
const selectedDentistId = ref(null)

onMounted( async () => {
  try {
    procedure.value = await ProcedureService.getProcedureById(procedureId)
    dentists.value = await DentistsService.getDentistByProcedureId(procedureId);
  } catch (error) {
    console.log("Error in AppointmentView on watch procedureId: " + error);
  }
});

function handleSelected(id) {
  selectedDentistId.value = id;
}

watch(selectedDentistId, async (id) => {

  if (id) {
    try {
      await router.push({name: 'AppointmentTimetable', params: {dentistId: id}})
    } catch (error) {
      console.log("Error in AppointmentView on watch procedureId: " + error);
    }
  }
})

</script>

<template>
  Selected procedure: {{procedure?.name}}<br>

  <div class="procedures-title-h2">
    <span>Select dentist: </span>
  </div>

  <div class="procedures-container">

    <div class="procedures-block">

      <div class="procedures-block-item">
        <div class="procedures-block-item-content" >
          <span class="procedures-block-item-labels">Icon</span>
        </div>
        <div class="procedures-block-item-content">
          <span class="procedures-block-item-labels">Name</span>
        </div>
        <div class="procedures-block-item-content">
          <span class="procedures-block-item-labels">Specialization</span>
        </div>
      </div>
      <div>
        <div
            v-for="dentist in dentists"
            :key="dentist.dentistId"
        >
          <DentistSmallCard
              :id="dentist.dentistId"
              :name="dentist.firstName + ' ' + dentist.patronymic + ' ' + dentist.lastName"
              :specialization="dentist.specialization"
              @dentistIdSelected="handleSelected"


          />
        </div>
        <div class="procedures-decoration-line"/>
      </div>
    </div>
  </div>


</template>

<style scoped>

</style>