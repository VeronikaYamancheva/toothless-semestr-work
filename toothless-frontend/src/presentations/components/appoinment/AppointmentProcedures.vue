<script setup>

import { useRouter } from 'vue-router'
import {ref, watch} from "vue";
import ProcedureView from "@/presentations/views/procedure/ProcedureView.vue";

const router = useRouter()

const selectedProcedureId = ref(null)

watch(selectedProcedureId, async (id) => {
  if (id) {
    try {
      await router.push({name: 'AppointmentDentists', params: {procedureId: id}})
    } catch (error) {
      console.log("Error in AppointmentView on watch procedureId: " + error);
    }
  }
})

function handleSelected(id) {
  selectedProcedureId.value = id;
}
</script>

<template>

    <ProcedureView
        :is-appointment="true"
        @procedureIdSelected="handleSelected"
    />

</template>

<style scoped>

</style>