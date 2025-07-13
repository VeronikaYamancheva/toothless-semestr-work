<script setup>

import DentistCard from "@/presentations/components/dentists/DentistCard.vue";
import toothlessSurgeon from "@/assets/images/toothless/toothless-dentist-surgeon.png";

import { DentistsService } from "@/services/dentists.service";
import {computed, onMounted, ref} from "vue";

const dentists = ref({ content: [], totalPages: 0 });
const currentPage = ref(0);
const pageSize = 6;
const error = ref(null);

const totalPages = computed(() => dentists.value.totalPages || 0);

function createFullName(dentist) {
  return `${dentist.firstName} ${dentist.middleName} ${dentist.lastName}`;
}

const fetchProcedures = async () => {
  error.value = null;
  try {

    dentists.value = await DentistsService.getAllDentists(currentPage.value, pageSize);

  } catch (err) {
    console.error('Failed to load dentists: ' + err);
  }
};

const nextPage = () => {
  if (currentPage.value < totalPages.value - 1) {
    currentPage.value++;
    fetchProcedures();
  }
};

const prevPage = () => {
  if (currentPage.value > 0) {
    currentPage.value--;
    fetchProcedures();
  }
};

onMounted(fetchProcedures);

</script>

<template>

  <div class="dentists-container">
    <div class="dentists-layout">
      <div class="dentists-block">
        <div class="dentists-title">
          Our Dentists
        </div>
      </div>
      <div class="dentists-block">

        <ul class="dentists-card-table">
          <li
              v-for="dentist in dentists.content"
              :key="dentist"
          >
            <DentistCard
                :id="dentist?.dentistId"
                :full-name="createFullName(dentist)"
                :specialization="dentist?.specialization"
                :profile-photo="toothlessSurgeon"/>
          </li>
        </ul>
      </div>

      <div class="pagination-controls">
        <button
            @click="prevPage"
            :disabled="currentPage === 0"
            class="pagination-button left"
        >
          🠔
        </button>

        <span>Page {{ currentPage + 1 }} of {{ totalPages }}</span>

        <button
            @click="nextPage"
            :disabled="currentPage >= totalPages - 1"
            class="pagination-button right"
        >
          🠖
        </button>
      </div>

    </div>
  </div>



</template>

<style scoped>

</style>