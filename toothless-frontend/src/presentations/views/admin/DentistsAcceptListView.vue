<script setup>

import toothlessSadIcon from "@/assets/images/toothless/toothless-sad.png";
import {onMounted, ref} from "vue";
import {AdminService} from "@/services/admin.services";
import DentistAcceptListCard from "@/presentations/components/admin/DentistAcceptListCard.vue";

const dentists = ref([]);

onMounted( async () => {
  await onReset()

});

const onReset = async () => {
  try {
    dentists.value = await AdminService.getDentistsAccept()

  }
  catch (error) {
    console.error("Error at DentistAcceptListView at method onReset" + error);
  }
}

</script>

<template>

  <div class="dentists-accept-list-view">

    <span class="dentists-accept-list-title">DENTISTS ACCEPT LIST</span>

    <div class="dentists-accept-list-layout">

      <div v-if="dentists.length === 0" class="dentists-accept-list-field-empty">
        <img class="dentists-accept-list-field-empty-img" :src="toothlessSadIcon" alt="Toothless sad icon.">
        <span class="dentists-accept-list-field-empty-text"> At the moment, there are no users whose accounts need confirmation. </span>
      </div>

      <div v-else class="dentists-accept-list-item">
        <div class="dentists-accept-list-item-content" >
          <span class="dentists-accept-list-item-labels">Icon</span>
        </div>
        <div class="dentists-accept-list-item-content">
          <span class="dentists-accept-list-item-labels">Full name</span>
        </div>
        <div class="dentists-accept-list-item-content">
          <span class="dentists-accept-list-item-labels">Status</span>
        </div>
      </div>

      <div
          class="dentists-accept-list"
          v-for="dentist in dentists"
          :key="dentist.userId"
      >


        <DentistAcceptListCard
            :dentist="dentist"
            @update="onMounted"
        />


      </div>

    </div>
  </div>

</template>

<style scoped>

@import "@/assets/css/admin/dentists-accept-list.css";

</style>