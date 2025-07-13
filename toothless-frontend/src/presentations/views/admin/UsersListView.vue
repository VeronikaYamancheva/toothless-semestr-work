<script setup>

import toothlessSadIcon from "@/assets/images/toothless/toothless-sad.png";
import {computed, onMounted, ref} from "vue";
import {useAuthStore} from "@/store/auth.store";
import {AdminService} from "@/services/admin.services";

const authStore = useAuthStore()

const users = ref({ content: [], totalPages: 0 });
const currentPage = ref(0);
const pageSize = 10;
const pages = ref()
const hasAuthorities = ref()


const totalPages = computed(() => pages.value || 0);

const fetchUsers = async () => {
  try {

    console.log("Start fetchUsers at UsersListView")
    const AdminServiceResponse = await AdminService.getAllUsers(currentPage.value, pageSize);

    console.log(AdminServiceResponse)

    users.value = AdminServiceResponse.content

    pages.value = AdminServiceResponse.totalPages

  } catch (err) {
    console.error('Failed to load users: ' + err);
  }
};

const nextPage = () => {
  if (currentPage.value < totalPages.value - 1) {
    scrollToTop()
    currentPage.value++;
    fetchUsers();
  }
};

const prevPage = () => {
  if (currentPage.value > 0) {
    scrollToTop()
    currentPage.value--;
    fetchUsers();
  }
};

const scrollToTop = () => {
  window.scrollTo({
    top: 0,
    behavior: 'smooth'
  });
}

onMounted( () => {

  hasAuthorities.value = false

  for(const role of authStore.user.roles) {
    if (role === 'MASTER') {
      hasAuthorities.value = true
      break
    }
  }

  fetchUsers()
});

const onRoleUpdate = async (userId, isAdmin) => {
  try {
    await AdminService.updateUserRole(userId, !isAdmin)
  }
  catch (error) {
    console.error('Failed to update user role for userId: ' + userId + " and isAdmin: " + isAdmin);
  }
}

const onStateUpdate = async (userId, isBanned) => {
  try {
    await AdminService.updateUserState(userId, !isBanned)
  }
  catch (error) {
    console.error('Failed to update user role for userId: ' + userId + " and isBanned: " + isBanned);
  }
}

const getFullName = (user) => {
  if (user === null) return "Undefined name"
  else if (!user.middleName) return user.firstName + " " + user.lastName
  else return user.firstName + " " + user.middleName + " " + user.lastName
}


</script>

<template>

  <div class="procedures-container">

    <span class="procedures-title-h1">USERS LIST</span>

    <div class="procedures-block">

      <div class="procedures-block-item">
        <div class="procedures-block-item-content" >
          <span class="procedures-block-item-labels">Icon</span>
        </div>
        <div class="procedures-block-item-content" >
          <span class="procedures-block-item-labels">Full name</span>
        </div>
        <div class="procedures-block-item-content" >
          <span class="procedures-block-item-labels">Role</span>
        </div>
        <div class="procedures-block-item-content">
          <span class="procedures-block-item-labels">Banned</span>
        </div>
        <div
            v-if="hasAuthorities"
            class="procedures-block-item-content"
        >
          <span class="procedures-block-item-labels">Admin</span>
        </div>
      </div>

      <div
          v-for="user in users"
          :key="user"
      >
        <div class="procedures-decoration-line"/>
        <div class="procedures-block-item">
          <img class="procedure-item-img" :src="toothlessSadIcon" alt="Toothless sad icon."/>

          <span class="procedure-item-title">{{ getFullName(user) }}</span>

          <span v-for="role in user.roles" class="procedure-item-title">{{ role || ' ' }}</span>

          <div class="procedure-item-title">
            <label class="switch">
              <input
                  type="checkbox"
                  :checked="user.isBanned"
                  @click="onStateUpdate(user.userId, user.isBanned)"
              >
              <span class="slider round"/>
            </label>
          </div>
          <div
              v-if="hasAuthorities"
              class="procedure-item-title"
          >
            <label class="switch">
              <input
                  type="checkbox"
                  :checked="user.isAdmin"
                  @click="onRoleUpdate(user.userId, user.isAdmin)"
              >
              <span class="slider round"/>
            </label>
          </div>
        </div>
      </div>
      <div class="procedures-decoration-line"/>

      <div class="pagination-controls">
        <button
            @click="prevPage"
            :disabled="currentPage === 0"
            class="pagination-button left"
        >
          🠔
        </button>

        <span>Page {{ currentPage + 1 }} of {{ totalPages }}</span>

        <button
            @click="nextPage"
            :disabled="currentPage >= totalPages - 1"
            class="pagination-button right"
        >
          🠖
        </button>
      </div>

    </div>
  </div>

</template>

<style scoped>

@import "@/assets/css/admin/users-list.css";

</style>