<template>
  <div class="profile-view">

    <span class="profile-page-title-2">{{`This is your profile`}}</span>

    <div class="profile-add-field">
      <div class="profile-add-item">
        <button
            @click="onAvatarUpdate()"
            class="profile-add-button"
        >
          <img class="profile-add-button-img" :src="upload" alt="add_box">
          <span class="profile-add-button-text">{{`Upload photo`}}</span>
        </button>

      </div>
    </div>

    <ProfileLayout>

      <ProfileLabelBlock :title="`Basic Information`">
        <img class="profile-img" :src="userAvatar" :alt="toothlessIcon"/>
      </ProfileLabelBlock>

      <ProfileField :label="basicInformation.fullName.label" :user-info="basicInformation.fullName.value"/>
      <ProfileField :label="basicInformation.birthDate.label" :user-info="basicInformation.birthDate.value"/>
      <ProfileField :label="basicInformation.phoneNumber.label" :user-info="basicInformation.phoneNumber.value"/>
      <ProfileField :label="basicInformation.email.label" :user-info="basicInformation.email.value"/>

      <ProfileUpdateModalWindow
          :data="basicInformation"
          :type="`base`"
          @updated="updateProfileInfo()"
      />

    </ProfileLayout>

    <ProfileLayout v-if="basicInformation.profileType.value !== 'ADMIN'">

      <ProfileLabelBlock :title="`Additional Information`"/>

      <div v-if="basicInformation.profileType.value === 'CLIENT'" >
        <ProfileField :label="additionalUserInformation.passportFullNumber.label" :user-info="additionalUserInformation.passportFullNumber.value"/>
        <ProfileField :label="additionalUserInformation.passportIssuedBy.label" :user-info="additionalUserInformation.passportIssuedBy.value"/>
        <ProfileField :label="additionalUserInformation.passportIssueDate.label" :user-info="additionalUserInformation.passportIssueDate.value"/>
        <ProfileField :label="additionalUserInformation.passportDivisionCode.label" :user-info="additionalUserInformation.passportDivisionCode.value"/>
        <ProfileField :label="additionalUserInformation.policyNumber.label" :user-info="additionalUserInformation.policyNumber.value"/>
        <ProfileField :label="additionalUserInformation.snils.label" :user-info="additionalUserInformation.snils.value"/>

        <ProfileUpdateModalWindow
            :type="`client`"
            :data="additionalUserInformation"
            @updated="updateProfileInfo()"
        />

      </div>

      <div v-else-if="basicInformation.profileType.value === 'DENTIST'" >
        <ProfileField :label="additionalDentistInformation.specialization.label" :user-info="additionalDentistInformation.specialization.value"/>
        <ProfileField :label="additionalDentistInformation.experience.label" :user-info="additionalDentistInformation.experience.value"/>
        <ProfileField :label="additionalDentistInformation.education.label" :user-info="additionalDentistInformation.education.value"/>
        <ProfileField :label="additionalDentistInformation.about.label" :user-info="additionalDentistInformation.about.value"/>

        <ProfileUpdateModalWindow
            :type="`dentist`"
            :data="additionalDentistInformation"
            @updated="updateProfileInfo()"
        />

      </div>

    </ProfileLayout>

    <ProfileLayout>
      <ProfileLabelBlock :title="'Authentication providers'"/>

      <ProfileProviders
          :providers="providers"
      />

    </ProfileLayout>

    <UpdatePhotoModalWindow
        v-if="avatarUploadWindowIsOpen"
        :modal-window-label="`Upload avatar`"
        @close="avatarUploadWindowIsOpen = false"
    />

  </div>
</template>

<script setup>
import { provide, ref, onMounted} from 'vue'
import {ProfileService} from "@/services/profile.service";
import ProfileLayout from "@/presentations/components/profile/ProfileLayout.vue";
import ProfileField from "@/presentations/components/profile/ProfileField.vue";
import ProfileLabelBlock from "@/presentations/components/profile/ProfileLabelBlock.vue";
import toothlessIcon from "@/assets/images/toothless/toothless-white-bg-big.png";
import upload from "@/assets/images/feature/upload_fill_0.svg";
import ProfileUpdateModalWindow from "@/presentations/components/profile/ProfileUpdateModalWindow.vue";

import {
  nameRules,
  phoneRules,
  birthDateRules,
  passportNumberRules, issueDateRules, divisionCodeRules, policyNumberRules, snilsNumberRules
} from '@/utils/validators/update.profile';
import {useAuthStore} from "@/store/auth.store";
import UpdatePhotoModalWindow from "@/presentations/components/profile/UpdatePhotoModalWindow.vue";
import ProfileProviders from "@/presentations/components/profile/ProfileProviders.vue";

const authStore = useAuthStore()

const userAvatar = ref();

const basicInformation = ref ({
  profileIcon: {label: 'Profile icon', value: '', isChangeable: false, rules: ''},
  firstName: {label: 'First name', value: '', isChangeable: true, rules: nameRules},
  middleName: {label: 'Middle name', value: '', isChangeable: true, rules: nameRules},
  lastName: {label: 'Last name', value: '', isChangeable: true, rules: nameRules},
  fullName: {label: 'Full name', value: '', isChangeable: false, rules: ''},
  birthDate: {label: 'Date of birth', value: '', isChangeable: true, rules: birthDateRules},
  phoneNumber: {label: 'Phone number', value: '', isChangeable: true, rules: phoneRules},
  email: {label: 'Email', value: '', isChangeable: false, rules: ''},
  profileType: {label: 'Profile type', value: '', isChangeable: false, rules: ''},
});

const additionalUserInformation = ref ({
  passportFullNumber: {label: 'Passport number', value: '', isChangeable: true, rules: passportNumberRules},
  passportIssuedBy: {label: 'Issued by', value: '', isChangeable: true, rules: ''},
  passportIssueDate: {label: 'Date of issue', value: '', isChangeable: true, rules: issueDateRules},
  passportDivisionCode: {label: 'Division code', value: '', isChangeable: true, rules: divisionCodeRules},
  policyNumber: {label: 'Policy number', value: '', isChangeable: true, rules: policyNumberRules},
  snils: {label: 'SNILS', value: '', isChangeable: true, rules: snilsNumberRules},
});

const additionalDentistInformation = ref ({
  specialization: {label: 'Specialization', value: '', isChangeable: true, rules: ''},
  experience: {label: 'Experience', value: '', isChangeable: true, rules: ''},
  education: {label: 'Education', value: '', isChangeable: true, rules: ''},
  about: {label: 'Additional info', value: '', isChangeable: true, rules: ''},
});

const avatarUploadWindowIsOpen = ref()

onMounted(async () => {
  try {
    await updateProfileInfo()
  } catch (error) {
    console.error("profileService on mounted error: ", error)
  }
})

const onAvatarUpdate = () => {
  avatarUploadWindowIsOpen.value = true;
}

const uploadUserAvatar = async () => {

  const avatarResponse = await ProfileService.getProfileAvatar(authStore.user.userId);

  if (avatarResponse === 404) {
    return userAvatar.value = toothlessIcon
  }

  if (avatarResponse) {}
  const base64 = btoa(
      new Uint8Array(avatarResponse).reduce(
          (data, byte) => data + String.fromCharCode(byte), ''
      )
  );
  userAvatar.value = `data:image/png;base64,${base64}`;
}

const providers = ref([])

const shouldLoad = ref(false)

const triggerLoad = () => {
  shouldLoad.value = true
}

provide('shouldLoad', shouldLoad)

const updateProfileInfo = async () => {
  try {

    const response = await ProfileService.getProfile();
    providers.value = response.authProviders;

    response.authProviders !== null ? triggerLoad() : shouldLoad.value = false

    await uploadUserAvatar()

    basicInformation.value.firstName.value = response.firstName;
    basicInformation.value.middleName.value = response.middleName;
    basicInformation.value.lastName.value = response.lastName;
    basicInformation.value.birthDate.value = dateOfBirthFormator(response.birthDate);
    basicInformation.value.phoneNumber.value = response.phoneNumber;
    basicInformation.value.email.value = response.email;
    basicInformation.value.profileType.value = response.profileType

    if (response.middleName !== null) {
      basicInformation.value.fullName.value = basicInformation.value.firstName.value + ' '
          + basicInformation.value.middleName.value + ' ' + basicInformation.value.lastName.value;
    } else {
      basicInformation.value.fullName.value = basicInformation.value.firstName.value + ' ' + basicInformation.value.lastName.value;
    }

    if (basicInformation.value.profileType.value === 'CLIENT') {
      additionalUserInformation.value.passportFullNumber.value = response.passportFullNumber;
      additionalUserInformation.value.passportIssuedBy.value = response.passportIssuedBy;
      additionalUserInformation.value.passportIssueDate.value = response.passportIssueDate;
      additionalUserInformation.value.passportDivisionCode.value = response.passportDivisionCode;
      additionalUserInformation.value.policyNumber.value = response.policyNumber;
      additionalUserInformation.value.snils.value = response.snils;

    } else if (basicInformation.value.profileType.value === 'DENTIST') {
      additionalDentistInformation.value.specialization.value = response.specialization;
      additionalDentistInformation.value.experience.value = response.experience;
      additionalDentistInformation.value.education.value = response.education;
      additionalDentistInformation.value.about.value = response.about;
    }

  } catch (error) {
    console.error("Error of getting data from profileService", error);
  }
}

function dateOfBirthFormator(value) {
  if (value !== null) {
    const [year, month, day] = value.split('-')
    return `${day}.${month}.${year}`}
}

</script>

<style scoped>

</style>