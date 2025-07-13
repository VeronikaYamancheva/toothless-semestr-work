<script setup>

import AppointmentBlock from "@/presentations/components/appoinment/AppointmentBlock.vue";
import {useAuthStore} from "@/store/auth.store";
import {onMounted, ref} from "vue";
import ProcedureCard from "@/presentations/components/procedures/ProcedureCard.vue";

const authStore = useAuthStore()

const isClientView = ref()

onMounted( () => {
  try {
    if (authStore.user) {
      const id = authStore.user.userId;
      const roles = authStore.user.roles;

      for (const role of roles) {
        if (role === 'CLIENT') {
          isClientView.value = true;
        } else if (role === 'DENTIST') {
          isClientView.value = false;
        }
      }
    }
  }
  catch (error) {
    console.log("Error at onMounted at MyAppointment: " + error);
  }
})



</script>

<template>

  <div class="procedures-container">

    <h1>My appointment</h1>

    <div class="profile-container">
      <div class="profile-layout">
        <div>

          <AppointmentBlock :label-block="`Client upcoming`"/>
        </div>
      </div>

      <div class="profile-layout">
        <div>
          <AppointmentBlock :label-block="`Client past`" />
        </div>
      </div>
    </div>

    <div class="profile-container">
      <div class="profile-layout">
        <div>
          <AppointmentBlock :label-block="`Dentist upcoming`"/>
        </div>
      </div>

      <div class="profile-layout">
        <div>
          <AppointmentBlock :label-block="`Dentist past`"/>
        </div>
      </div>
    </div>

  </div>


</template>

<style scoped>


</style>

