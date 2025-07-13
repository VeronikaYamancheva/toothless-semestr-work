<script setup>
import {ref, computed, onBeforeUnmount, onMounted, inject} from 'vue'

const props = defineProps({
  specializations: {
    type: Array,
    required: true
  },
  placeholder: {
    type: String,
    default: 'Select specialization'
  },
  modelValue: {
    type: String,
    default: ''
  }
})

const emit = defineEmits(['update:modelValue'])

const isOpen = ref(false)
const isHovered = ref(null)
const myRef = ref(null)

function handleClick(event) {
  if (myRef.value && !myRef.value.contains(event.target)) {
    isOpen.value = false
  }
}

const selectedSpecialization = computed(() => {

  if (!props.modelValue) {
    return null
  }
  if (typeof props.modelValue === 'object') {
    return props.modelValue
  }
  if (!Array.isArray(props.specializations)) {
    console.warn("props.specializations не массив или не определён");
    return null;
  }
  const found = props.specializations.find(special => {
    return special.specializationId === props.modelValue;
  });
  return found || null;
})

onMounted( () => {
  if (props.modelValue) {
  }
})

const displayValue = computed(() => {
  return selectedSpecialization.value?.name || props.placeholder
})

const selectOption = (specialization) => {
  emit('update:modelValue', specialization.specializationId)
  isOpen.value = false
}

const toggleDropdown = (event) => {
  event.stopPropagation()
  isOpen.value = !isOpen.value
}

onMounted(() => {
  document.addEventListener('click', handleClick)
})
onBeforeUnmount(() => {
  document.removeEventListener('click', handleClick)
})
</script>

<template>
  <div ref="myRef" class="custom-select">
    <div
        class="update-procedure-input-field pad-top"
        @click="toggleDropdown"
    >
      {{ displayValue }}
    </div>
    <div class="options" v-show="isOpen">
      <div
          v-for="specialization in specializations"
          :key="specialization.name"
          @click="selectOption(specialization)"
          :class="{ 'hover-specialization': isHovered === specialization.name }"
          @mouseover="isHovered = specialization.name"
          @mouseout="isHovered = null"
      >
        {{ specialization.name }}
      </div>
    </div>
  </div>
</template>


<style scoped>

</style>