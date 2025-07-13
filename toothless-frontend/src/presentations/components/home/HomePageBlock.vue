<script setup>

/*global defineProps*/

defineProps({
  blockId: {
    type: Number,
    default: 0,
  },
  blockTitle: {
    type: String,
    default: '',
  },
  buttonLabel: {
    type: String,
    default: '',
  },
  buttonPath: {
    type: String,
    default: '',
  }
},
)

</script>

<template>

  <div class="home-page-block">

    <div v-if="blockTitle === `Toothless Dental Clinic`" align="center" class="home-page-title">
      {{blockTitle}}
    </div>

    <div v-else class="home-page-block-title" align="center">
      {{blockTitle}}
    </div>

    <slot/>

  </div>

</template>

<style scoped>

@import '@/assets/css/home/toothless-home-page.css';

</style>