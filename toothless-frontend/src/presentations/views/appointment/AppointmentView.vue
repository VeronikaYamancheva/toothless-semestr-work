<script setup>
import AppointmentNavBarComponent from "@/presentations/components/appoinment/AppointmentNavBarComponent.vue";

</script>

<template>
  <div class="appointment-container">
    <div class="appointment-block">
      <AppointmentNavBarComponent/>

      <router-view />

    </div>
  </div>



</template>

<style scoped>

@import "@/assets/css/appointment/appointment-style.css";

</style>