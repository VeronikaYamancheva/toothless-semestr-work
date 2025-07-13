<script setup>

/* global defineProps */

defineProps({
  id: {
    type: String,
  },
  fullName: {
    type: String,
  },
  specialization: {
    type: String,
  },
  profilePhoto: {
    type: String
  }
});

import {onBeforeUnmount, onMounted, ref} from "vue";

const scrollContainer = ref(null);
const SMALL_SCROLL_STEP = 600;
const AUTO_SCROLL_DELAY = 10000;

let inactivityTimeout = null;

const scrollLeft = () => {
  scrollContainer.value.scrollBy({ left: -SMALL_SCROLL_STEP, behavior: 'smooth' });
};

const scrollRight = () => {
  scrollContainer.value.scrollBy({ left: SMALL_SCROLL_STEP, behavior: 'smooth' });
};

const autoScroll = () => {
  const container = scrollContainer.value;
  if (!container) return;

  const maxScrollLeft = container.scrollWidth - container.clientWidth;

  if (container.scrollLeft + 10 >= maxScrollLeft) {
    container.scrollTo({ left: 0, behavior: 'smooth' });
  } else {
    container.scrollBy({ left: SMALL_SCROLL_STEP, behavior: 'smooth' });
  }

  startAutoScroll();
};

const startAutoScroll = () => {
  inactivityTimeout = setTimeout(autoScroll, AUTO_SCROLL_DELAY);
};

const resetAutoScroll = () => {
  if (inactivityTimeout) clearTimeout(inactivityTimeout);
  startAutoScroll();
};

onMounted(() => {
  startAutoScroll();
  scrollContainer.value?.addEventListener('scroll', resetAutoScroll);
});

onBeforeUnmount(() => {
  if (inactivityTimeout) clearTimeout(inactivityTimeout);
  scrollContainer.value?.removeEventListener('scroll', resetAutoScroll);
});

</script>

<template>

  <div class="home-page-scroll-wrapper">

    <button class="home-page-scroll-btn left" @click="scrollLeft">
      🠔
    </button>

    <div class="home-page-scroll-container" ref="scrollContainer">

      <slot/>

    </div>

    <button class="home-page-scroll-btn right" @click="scrollRight">
      🠖
    </button>

  </div>

</template>

<style scoped>

@import '@/assets/css/home/toothless-home-page.css';

</style>