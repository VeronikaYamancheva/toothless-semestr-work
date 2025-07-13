<script setup>
import { ref } from 'vue'

const visible = ref(false)
const message = ref('')

const show = (msg) => {
  message.value = msg
  visible.value = true
}

const close = () => {
  visible.value = false
}

defineExpose({ show })

</script>

<template>

  <Teleport to="body">
    <div
        v-if="visible"
        class="alert-overlay"
    >
      <div class="alert-box">
        <div class="alert-message">
          {{ message }}
        </div>
        <button
            @click="close"
            class="alert-button"
        >
          Confirm!
        </button>
      </div>
    </div>
  </Teleport>

</template>

<style scoped>

@import "@/assets/css/constants/alert-component.css";

</style>