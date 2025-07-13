<script setup>

import { useRoute, useRouter } from 'vue-router'
import {computed, inject} from "vue";

const route = useRoute()
const router = useRouter()

const alert = inject('alert')

const steps = [
  { label: 'Procedure', name: 'AppointmentProcedures' },
  { label: 'Dentist', name: 'AppointmentDentists' },
  { label: 'Timetable', name: 'AppointmentTimetable' },
]

const currentStepIndex = computed(() => {
  const path = route.name

  if (path === 'AppointmentProcedures') return 0
  if (path === 'AppointmentDentists') return 1
  if (path === 'AppointmentTimetable') return 2
  return -1
})

const onStep = (index) => {
  if (currentStepIndex.value < index) {
    currentStepIndex.value === 1 ? alert('Before go to the next step, select the dentist you are going to make an appointment with') :
        alert('Before go to the next step, select the procedure you are going to make an appointment with');
  }
  else {
    goToStep(index)
  }
}

function goToStep(index) {
  if (index === currentStepIndex.value) return

  const procedureId = route.params.procedureId
  const dentistId = route.params.dentistId

  if (index === 0) {
    router.push({ name: 'AppointmentProcedures' })
  } else if (index === 1 ) {
    router.push({ name: 'AppointmentDentists' })
  } else if (index === 2 ) {
    router.push({ name: 'AppointmentTimetable' })
  }
}

</script>

<template>

  <div class="stepper">

    <div class="stepbar">
      <ul class="steps">
        <li
            v-for="(step, index) in steps"
            :key="step.name"
            :class="{ active: index === currentStepIndex }"
            @click="onStep(index)"
        >
          {{ step.label }}
        </li>
      </ul>
    </div>
  </div>

</template>

<style scoped>
.steps {
  display: flex;
  gap: 20px;
  list-style: none;
  padding: 0;
  margin-bottom: 20px;
}

.steps li {
  cursor: pointer;
  padding: 10px 20px;
  border-bottom: 2px solid transparent;
}

.steps li.active {
  font-weight: bold;
  border-color: #007bff;
}

</style>