<template>
  <header class="header">
    <nav>
      <div class="nav-links">
        <router-link class="visible" to="/">
          <img class="header-img" :src="toothlessIcon" alt="Image loading error =("/>
        </router-link>
        <router-link class="visible" to="/">
          <span class="header-logo">Toothless</span>
        </router-link>
      </div>

      <div class="nav-links">
        <DropDownMenu
            v-if="isAuthenticated"
            :label="`Account ▾`"
            :routes="account"
            :id="`account`"
            :open-dropdown="openDropdown"
            @toggle="handleDropdownToggle"
        />
        <DropDownMenu
            :label="`Menu ▾`"
            :routes="menu"
            :id="`menu`"
            :open-dropdown="openDropdown"
            @toggle="handleDropdownToggle"
        />
        <DropDownMenu
            v-if="hasAuthorities"
            :label="`Admin ▾`"
            :routes="admin"
            :id="`admin`"
            :open-dropdown="openDropdown"
            @toggle="handleDropdownToggle"
        />
        <DropDownMenu
            v-if="!isAuthenticated"
            :label="`Log In ▾`"
            :routes="login"
            :id="`login`"
            :open-dropdown="openDropdown"
            @toggle="handleDropdownToggle"
        />
        <LogOutComponent :is-authenticated="isAuthenticated"/>
      </div>

    </nav>
  </header>
</template>

<script setup>
import { useAuthStore } from '@/store/auth.store';
import { storeToRefs } from 'pinia';
import toothlessIcon from '@/assets/images/toothless/toothless-white-bg-big.png'
import LogOutComponent from "@/presentations/components/auth/LogOutComponent.vue";
import {onMounted, ref} from "vue";
import DropDownMenu from "@/presentations/components/layout/DropDownMenu.vue";

const authStore = useAuthStore();
const { isAuthenticated } = storeToRefs(authStore);

const login = ref([
  { route: "/login", label: "Log In" },
  { route: "/register", label: "Registration" },
])

const admin = ref([
  { route: "/dentists/accept", label: "Accept dentists" },
  { route: "/appointments/list", label: "All appointments" },
  { route: "/users", label: "All users" },
  { route: "/procedures/management", label: "Procedures management" },
])

const menu = ref([
  { route: "/procedures", label: "Procedures" },
  { route: "/dentists", label: "Dentists" },
  { route: "/appointment/procedures", label: "Appointment" },
])

const account = ref([
  { route: "/profile", label: "Profile" },
])

const hasAuthorities = ref()

const hasAuth = () => {

  if (authStore.user !== null) {
    for (const role in authStore.user.roles) {
      if (authStore.user.roles[role] === "MASTER" || authStore.user.roles[role] === "ADMIN") {
        hasAuthorities.value = true;
      }
    }
  }

  console.log(hasAuthorities.value)

}

onMounted(  () => {
   hasAuth()
})

const openDropdown = ref(null)

const handleDropdownToggle = (id) => {
  openDropdown.value = openDropdown.value === id ? null : id
}

</script>

<style scoped>

@import '@/assets/css/constants/header-component.css';

</style>