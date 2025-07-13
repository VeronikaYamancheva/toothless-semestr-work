<script setup>
import { ref, computed, onMounted } from 'vue';
import { ProcedureService } from "@/services/procedure.service";
import ProcedureCard from "@/presentations/components/procedures/ProcedureCard.vue";

/* global defineProps */
/* global defineEmits */

defineProps({
  isAppointment: Boolean,
  blockLabel: String,
});

const procedures = ref({ content: [], totalPages: 0 });
const currentPage = ref(0);
const pageSize = 10;
const error = ref(null);

const emit = defineEmits(['procedureIdSelected']);

const totalPages = computed(() => procedures.value.totalPages || 0);

const fetchProcedures = async () => {
  error.value = null;
  try {

    procedures.value = await ProcedureService.getAllProcedures(currentPage.value, pageSize);


  } catch (err) {
    console.error(err);
  }
};

const nextPage = () => {
  if (currentPage.value < totalPages.value - 1) {
    scrollToTop()
    currentPage.value++;
    fetchProcedures();
  }
};

const prevPage = () => {
  if (currentPage.value > 0) {
    scrollToTop()
    currentPage.value--;
    fetchProcedures();
  }
};

const scrollToTop = () => {
  window.scrollTo({
    top: 0,
    behavior: 'smooth'
  });
}

onMounted(fetchProcedures);
</script>

<template>

  <div v-if="isAppointment" class="procedures-title-h2">
    <span>{{`Select procedure: `}}</span>
  </div>

  <div class="procedures-container">

    <div v-if="!isAppointment" class="procedures-title-h1">
      <span>{{`PROCEDURE LIST`}}</span>
    </div>

    <div
        class="procedures-block"
    >

      <div class="procedures-block-item">
        <div class="procedures-block-item-content" >
          <span class="procedures-block-item-labels"> {{`Preview`}} </span>
        </div>
        <div class="procedures-block-item-content">
          <span class="procedures-block-item-labels">{{`Procedure`}}</span>
        </div>
        <div class="procedures-block-item-content">
          <span class="procedures-block-item-labels">{{`Price`}}</span>
        </div>
      </div>
      <div>
        <div
            v-for="procedure in procedures.content"
            :key="procedure.procedureId"
        >
          <ProcedureCard
              :id="procedure.procedureId"
              :name="procedure.name"
              :price="procedure.cost"
              :is-appointment="isAppointment"
              @procedureIdSelected="emit('procedureIdSelected', $event)"


          />
        </div>
        <div class="procedures-decoration-line"/>

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
  </div>

</template>

<style scoped>

@import "@/assets/css/procedures/procedures-style.css";

</style>