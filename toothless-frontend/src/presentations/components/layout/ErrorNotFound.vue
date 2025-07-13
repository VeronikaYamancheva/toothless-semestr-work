<script setup>

import toothless from "@/assets/images/toothless/toothless-error.png";


</script>

<template>

  <div class="error-page">

    <div class="error-page-1">
      <div class="procedures-block-item">
          <img class="error-page-img" :src="toothless" alt="Error 404"/>
        <div class="error-page-text"><b>ERROR 404</b></div>
      </div>
    </div>

  </div>

</template>

<style scoped>

@import "@/assets/css/procedures/procedures-style.css";

.error-page {
  width: 100%;
  height: 100%;
  background-color: #d0c8bc;
}

.error-page-1 {
  width: 100%;
  height: 100%;
  background-color: var(--white-color);
}

.error-page-img {
  position: fixed;
  top:0;
  left: 0;
  max-width: 113%;
  height: auto;
  display: block;
}

.error-page-text {
  font-size: var(--standart-font-family);
  z-index: 9999;
  position: fixed;
  font-size: 7rem;
  top: 15rem;
  right: 4rem;
}

</style>