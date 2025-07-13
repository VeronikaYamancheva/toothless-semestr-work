<template>
  <div class="app">
    <HeaderComponent />
    <main class="main-content">
      <router-view />
    </main>
    <FooterComponent />
  </div>
</template>

<script setup>
import HeaderComponent from '@/presentations/components/layout/HeaderComponent.vue';
import FooterComponent from '@/presentations/components/layout/FooterComponent.vue';
</script>

<style>

@import "@/assets/css/app-style.css";

</style>