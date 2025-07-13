<script setup>
import {ref, computed, onMounted, inject} from 'vue';
import { ProcedureService } from "@/services/procedure.service";
import ProcedureManagementCard from "@/presentations/components/procedures/ProcedureManagementCard.vue";
import add_box from "@/assets/images/feature/add_box_fill_1.svg";
import ProcedureDetailedModalWindow from "@/presentations/components/procedures/ProcedureDetailedModalWindow.vue";

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
const isCreatingWindowOpen = ref(false);

const emit = defineEmits(['procedureIdSelected']);

const totalPages = computed(() => procedures.value.totalPages || 0);

const fetchProcedures = async () => {
  error.value = null;
  try {

    procedures.value = await ProcedureService.getAllProcedures(currentPage.value, pageSize);


  } catch (err) {
    error.value = 'Failed to load procedures. Please try again later.';
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

onMounted( () => {
  try {
    fetchProcedures()
  }
  catch (error) {
    console.log("Error at ProcedureManagementView at onMounted", error);
  }
});

const onClose = () => {
  isCreatingWindowOpen.value = false
  currentPage.value = 0
  fetchProcedures()
}

const onNewCreate = () => {
  isCreatingWindowOpen.value = !isCreatingWindowOpen.value;
}

</script>

<template>

  <div class="procedures-container">

    <div class="procedures-title-h1">
      <span>PROCEDURE MANAGEMENT</span>
    </div>

    <div class="management-add-field">
      <div class="management-add-item">
        <button
            @click="onNewCreate()"
            class="management-add-button"
        >
          <img class="management-add-button-img" :src="add_box" alt="add_box">
          <span class="management-add-button-text">New procedure</span>
        </button>

      </div>
    </div>

    <div
        class="procedures-block"
    >

      <div class="procedures-block-item">
        <div class="procedures-block-item-content" >
          <span class="procedures-block-item-labels">Preview</span>
        </div>
        <div class="procedures-block-item-content">
          <span class="procedures-block-item-labels">Procedure</span>
        </div>
        <div class="procedures-block-item-content">
          <span class="procedures-block-item-labels">Price</span>
        </div>
        <div class="procedures-block-item-content">
          <span class="procedures-block-item-labels">Settings</span>
        </div>
      </div>
      <div>
        <div
            v-for="procedure in procedures.content"
            :key="procedure.procedureId"
        >
          <ProcedureManagementCard
              :id="procedure.procedureId"
              :name="procedure.name"
              :price="procedure.cost"
              :is-appointment="isAppointment"
              @procedureIdSelected="emit('procedureIdSelected', $event)"
              @close="onClose()"

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

  <ProcedureDetailedModalWindow
      v-if="isCreatingWindowOpen"
      :type="`create`"
      :label="`New procedure`"
      @close="onClose()"
  />

</template>

<style scoped>

@import "@/assets/css/procedures/procedures-style.css";

</style>