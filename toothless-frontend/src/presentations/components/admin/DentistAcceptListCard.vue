<script setup>

import toothlessIcon from "@/assets/images/toothless/toothless-white-bg-big.png";
import {onMounted, ref} from "vue";
import {ProfileService} from "@/services/profile.service";
import {AdminService} from "@/services/admin.services";

const props = defineProps({
  dentist: Array,
  modelValue: Boolean,
})

const fullName = ref()
const dentistAvatar = ref()
const emit = defineEmits(['upd'])

onMounted(() => {
  if (props.dentist.middleName) {
    fullName.value = props.dentist.firstName + ' ' + props.dentist.middleName + ' ' + props.dentist.lastName
  }
  else {
    fullName.value = props.dentist.firstName + ' ' + props.dentist.lastName
  }

  uploadUserAvatar(props.dentist.userId)

})

const uploadUserAvatar = async (userId) => {
  try {
    const avatarResponse = await ProfileService.getProfileAvatar(userId);
    if (avatarResponse !== 404) {
      const base64 = btoa(
          new Uint8Array(avatarResponse).reduce(
              (data, byte) => data + String.fromCharCode(byte),
              ''
          )
      );
      dentistAvatar.value = `data:image/png;base64,${base64}`;
    } else {
      dentistAvatar.value = toothlessIcon;
    }
  } catch (error) {
    console.error("Ошибка при загрузке аватара:", error);
    dentistAvatar.value = toothlessIcon;
  }
};

const onAccept = async (isAccept) => {

  console.log("Start onAccept at DentistsAcceptListView with dentistId: " + props.dentist.userId + " and status isAccept: " + isAccept)

  await AdminService.acceptDentist(props.dentist.userId, isAccept)

  emit('upd')
}

</script>

<template>

  <div class="dentists-accept-list-decoration-line"/>
  <div class="dentists-accept-list-item">
    <div class="dentists-accept-list-item-content">
      <img class="dentists-accept-list-item-img" :src="dentistAvatar" alt="User avatar."/>
    </div>
    <div class="dentists-accept-list-item-content">
      <span class="dentists-accept-list-item-text">{{ fullName }}</span>
    </div>
    <div class="dentists-accept-list-item-content">
      <button class="dentists-accept-list-button dentists-accept-list-accept" @click="onAccept( true)">Accept</button>
      <button class="dentists-accept-list-button dentists-accept-list-reject" @click="onAccept( false)">Reject</button>
    </div>
  </div>

</template>

<style scoped>

</style>