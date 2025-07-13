<script setup>

import {computed, onMounted, ref} from "vue";
import {ProcedureService} from "@/services/procedure.service";
import toothlessWithToothBrash from "@/assets/images/toothless/toothless-with-toothbrush.png";
import toothlessSad from "@/assets/images/toothless/toothless-sad.png";
import SpecializationDropDown from "@/presentations/components/dentists/SpecializationDropDown.vue";
import {DentistsService} from "@/services/dentists.service";
import {
  procedureCostRule,
  procedureDescriptionRule,
  procedureNameRule,
  procedureSpecializationRule
} from "@/utils/validators/procedure";

const props = defineProps({
  procedureId: String,
  type: String,
  label: String,
})

const emit = defineEmits(["close"]);

const modalWindowLabel = ref()
const procedure = ref({
  name: '',
  description: '',
  cost: '',
  specializationId: ''
})
const specializations = ref()

onMounted( async () => {
  try {

    switch (props.type) {
      case "create":
        if (procedure.value.specializationId) {
          procedure.value.specializationId = null
        }
        await onLoadSpecialization()
        break;
      case "read":
        await onRead(props.procedureId)
        break;
      case "update":
        await onRead(props.procedureId)
        await onLoadSpecialization()
        break;
      case "delete":
        break;
    }

    modalWindowLabel.value = props.label

  }
  catch (error) {
    console.log("Error at procedureDetailed:" + error)
  }
});

const onCreate = async () => {
  try {
    const formData = new FormData();

    formData.append('name', procedure.value.name)
    formData.append('description', procedure.value.description)
    formData.append('cost', procedure.value.cost)
    formData.append('specializationId', procedure.value.specializationId)
    procedure.value = await ProcedureService.createNewProcedure(formData);
    onClose()
  }
  catch (error) {
    console.log("Error at ProcedureDetailedModalView at method onCreate: ", error)
  }
}

const onLoadSpecialization = async () => {
  try {
    specializations.value = await DentistsService.getDentistsSpecialization()
  }
  catch (error) {
    console.log("Error at ProcedureDetailedModalView at method onDelete: ", error)
  }
}

const onRead = async (readingProcedureId) => {
  try {
    if (readingProcedureId) {
      procedure.value = await ProcedureService.getProcedureById(readingProcedureId);
    }

  }
  catch (error) {
    console.log("Error at ProcedureDetailedModalView at method onRead: ", error)
  }
}

const onUpdate = async (updatingProcedureId) => {
  try {

    const formData = new FormData();

    formData.append('name', procedure.value.name)
    formData.append('description', procedure.value.description)
    formData.append('cost', procedure.value.cost)
    formData.append('specializationId', procedure.value.specializationId)
    procedure.value = await ProcedureService.updateProcedureById(updatingProcedureId, formData);

    onClose()

  }
  catch (error) {
    console.log("Error at ProcedureDetailedModalView at method onUpdate: ", error)
  }
}

const onDelete = async (deletingProcedureId) => {
  try {
    const ProcedureServiceResponse = await ProcedureService.deleteProcedureById(deletingProcedureId)
    emit('close')
  }
  catch (error) {
    console.log("Error at ProcedureDetailedModalView at method onDelete: ", error)
  }
}

const onClose = () => {
  emit('close')
}

const isFormValid = computed(() =>
    !nameErrorRaw.value && !descriptionErrorRaw.value && !priceErrorRaw.value && !specializationErrorRaw.value);

function useValidation(valueRef, rules) {
  const touched = ref(false);
  const errorRaw = computed(() => {
    for (const rule of rules) {
      const result = rule(valueRef.value);
      if (result !== true) return result;
    }
    return '';
  });
  const error = computed(() => (touched.value ? errorRaw.value : ''));
  return { touched, errorRaw, error };
}

const { touched: nameTouched, errorRaw: nameErrorRaw, error: nameError } = useValidation(
    computed(() => procedure.value.name),
    procedureNameRule
);
const { touched: descriptionTouched, errorRaw: descriptionErrorRaw, error: descriptionError } = useValidation(
    computed(() => procedure.value.description),
    procedureDescriptionRule
);

const { touched: priceTouched, errorRaw: priceErrorRaw, error: priceError } = useValidation(
    computed(() => procedure.value.cost),
    procedureCostRule
);
const { touched: specializationTouched, errorRaw: specializationErrorRaw, error: specializationError } = useValidation(
    computed(() => procedure.value.specializationId),
    procedureSpecializationRule
);

</script>

<template>

  <div
      class="procedure-modal-window-overlay"
      @click.self="emit('close')"
  >

    <div
        class="procedure-modal-window-field"
        :class="{'small': type === 'read' || type === 'delete', 'large': type === 'create' || type === 'update'}"
    >

      <div class="procedure-modal-window-item-right">

        <button
            class="procedure-modal-window-close-button"
            @click="emit('close')"
        >
          ×
        </button>

      </div>

      <div class="procedure-modal-window-field-title">
        <span>{{ modalWindowLabel }}</span>
      </div>

      <form
          v-if="type === 'create'"
          @submit.prevent="onCreate()"
      >

        <div class="profile-decoration-line" />
        <div class="update-procedure-field">
          <span class="update-procedure-field-label">Procedure name</span>
          <div class="update-procedure-decoration-line-vertical" />
          <div class="update-procedure-field-info">

            <input
                class="update-procedure-input-field"
                type="text"
                v-model="procedure.name"
                @blur="nameTouched = true"/>

            <span v-if="nameError" class="update-procedure-error-message"> {{ nameError }}</span>

          </div>
        </div>
        <div class="profile-decoration-line" />
        <div class="update-procedure-field">
          <span class="update-procedure-field-label">Price (in USD)</span>
          <div class="update-procedure-decoration-line-vertical" />
          <div class="update-procedure-field-info">

            <input
                class="update-procedure-input-field"
                type="text"
                v-model="procedure.cost"
                @blur="priceTouched = true"/>

            <span v-if="priceError" class="update-procedure-error-message"> {{ priceError }}</span>


          </div>
        </div>

        <div class="profile-decoration-line" />
        <div class="update-procedure-field">
          <span class="update-procedure-field-label">Specialization</span>
          <div class="update-procedure-decoration-line-vertical" />
          <div class="update-procedure-field-info">

            <SpecializationDropDown
                :specializations="specializations"
                v-model="procedure.specializationId"
                @blur="specializationTouched = true"/>

            <span v-if="specializationError" class="update-procedure-error-message"> {{ specializationError }}</span>

          </div>
        </div>
        <div class="profile-decoration-line" />
        <div class="update-procedure-field">
          <span class="update-procedure-field-label">Description</span>
          <div class="update-procedure-decoration-line-vertical" />
          <div class="update-procedure-field-info">

            <textarea
                class="procedure-modal-textarea"
                v-model="procedure.description"
                @blur="descriptionTouched = true"/>


            <span v-if="descriptionError" class="update-procedure-error-message"> {{ descriptionError }}</span>

          </div>
        </div>
        <div class="profile-decoration-line" />

        <div class="procedure-modal-window-item-center">
          <button :disabled="!isFormValid" type="submit" class="procedure-modal-window-field-button">Create</button>
          <button @click="emit('close')" class="procedure-modal-window-field-button">Leave</button>
        </div>

      </form>

      <div
          v-if="type === 'read'"
          class="procedure-modal-window-item-left"
      >

        <div>
          <div class="procedure-modal-window-item-center">
            <img class="procedure-modal-window-img" :src="toothlessWithToothBrash" alt="Procedure icon"/>
          </div>

          <div
              class="procedure-modal-field-text"
          >
            <span class="procedure-modal-field-label">Price: </span>
            {{ procedure?.cost }}$
          </div>

          <div
              v-if="procedure?.description"
              class="procedure-modal-field-text"
          >
            <span class="procedure-modal-field-label">Description: </span>
            {{ procedure?.description }}
          </div>
          <div
              v-else
              class="procedure-modal-window-text-description"
          >
            {{ 'No description available' }}
          </div>

        </div>
      </div>

      <form
          v-if="type === 'update'"
          @submit.prevent="onUpdate(procedureId)"
      >

        <div class="profile-decoration-line" />
        <div class="update-procedure-field">
          <span class="update-procedure-field-label">Procedure name</span>
          <div class="update-procedure-decoration-line-vertical" />
          <div class="update-procedure-field-info">

            <input
                class="update-procedure-input-field"
                type="text"
                v-model="procedure.name"
                @blur="nameTouched = true"/>

            <span v-if="nameError" class="update-procedure-error-message"> {{ nameError }}</span>

          </div>
        </div>
        <div class="profile-decoration-line" />
        <div class="update-procedure-field">
          <span class="update-procedure-field-label">Price (in USD)</span>
          <div class="update-procedure-decoration-line-vertical" />
          <div class="update-procedure-field-info">

            <input
                class="update-procedure-input-field"
                type="text"
                v-model="procedure.cost"
                @blur="priceTouched = true"/>

            <span v-if="priceError" class="update-procedure-error-message"> {{ priceError }}</span>


          </div>
        </div>

        <div class="profile-decoration-line" />
        <div class="update-procedure-field">
          <span class="update-procedure-field-label">Specialization</span>
          <div class="update-procedure-decoration-line-vertical" />
          <div class="update-procedure-field-info">

            <SpecializationDropDown
                :specializations="specializations"
                v-model="procedure.specializationId"
                @blur="specializationTouched = true"/>


            <span v-if="specializationError" class="update-procedure-error-message"> {{ specializationError }}</span>

          </div>
        </div>
        <div class="profile-decoration-line" />
        <div class="update-procedure-field">
          <span class="update-procedure-field-label">Description</span>
          <div class="update-procedure-decoration-line-vertical" />
          <div class="update-procedure-field-info">

            <textarea
                class="procedure-modal-textarea"
                v-model="procedure.description"
                @blur="descriptionTouched = true"/>


            <span v-if="descriptionError" class="update-procedure-error-message"> {{ descriptionError }}</span>

          </div>
        </div>
        <div class="profile-decoration-line" />

        <div class="procedure-modal-window-item-center">
          <button :disabled="!isFormValid" type="submit" class="procedure-modal-window-field-button">Create</button>
          <button @click="emit('close')" class="procedure-modal-window-field-button">Leave</button>
        </div>

      </form>

      <div
          v-if="type === 'delete'"
      >
        <div
            class="procedure-modal-window-item-center"
        >
          <div class="procedure-modal-window-center">
            <div class="procedure-modal-window-item-center">
              <img class="procedure-modal-window-img" :src="toothlessSad" alt="Sad toothless"/>
            </div>

            <div
                class="procedure-modal-field-text"
            >
              <span
                  class="procedure-modal-field-label"
              >
                Are you sure you want to delete this procedure?
              </span>
            </div>
          </div>
        </div>
        <div class="procedure-modal-window-item-center">
          <button @click="onDelete(procedureId)" class="procedure-modal-window-field-button">Yes</button>
          <button @click="onClose()" class="procedure-modal-window-field-button">No</button>
        </div>
      </div>
    </div>
  </div>

</template>

<style scoped>

@import "@/assets/css/procedures/procedure-detailed-window.css";

</style>