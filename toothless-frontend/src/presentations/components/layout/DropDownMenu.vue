<script setup>

import {computed, onBeforeUnmount, onMounted, ref} from "vue";

const props = defineProps({
  label: String,
  routes: Array,
  id: String,
  openDropdown: String
})

const emit = defineEmits(['toggle'])

const open = computed(() => props.openDropdown === props.id)

const toggle = (e) => {
  e.stopPropagation()
  emit('toggle', props.id)
}
const close = () => emit('toggle', null)

const handleClickOutside = (e) => {
  if (!e.target.closest('.dropdown')) close()
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
})
onBeforeUnmount(() => {
  document.removeEventListener('click', handleClickOutside)
})
</script>

<template>

  <div
      class="dropdown"
      @click="toggle"
  >
    <button
        class="dropdown-toggle "
        :class="{'selected': open}"
    >
      {{ label }}
    </button>
    <div v-if="open" class="dropdown-menu" :class="{show: open}">
      <div
          v-for="route in routes"
          :key="route"
      >
        <router-link class="dropdown-item" :to="route.route" @click="close">{{ route.label }}</router-link>
      </div>
    </div>
  </div>

</template>

<style scoped>

</style>