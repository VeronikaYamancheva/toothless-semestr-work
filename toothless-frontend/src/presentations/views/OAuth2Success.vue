<script setup>
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/store/auth.store';

const router = useRouter();
const authStore = useAuthStore();

onMounted(async () => {
  try {
    await authStore.refresh();
    await router.push('/profile');
  } catch (e) {
    await router.replace('/login');
  }
});
</script>

<template>
  <div>Loading...</div>
</template>
