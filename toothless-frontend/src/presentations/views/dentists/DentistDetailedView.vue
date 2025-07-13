<script setup>

import toothlessSurgeon from "@/assets/images/toothless/toothless-dentist-surgeon.png";

import { useRoute } from 'vue-router';
import {DentistsService} from "@/services/dentists.service";
import {onMounted, ref} from "vue";
import CommentBlock from "@/presentations/components/comment/CommentBlock.vue";
import TimetableComponent from "@/presentations/components/timetable/TimetableComponent.vue";

const route = useRoute();
const dentistId = route.params.id;
const dentist = ref();

onMounted( async () => {
  try {
    dentist.value = await DentistsService.getDentistById(dentistId);

  }
  catch (error) {
    console.log(error);
  }
});

function getAge(birthDateString) {
  const today = new Date();
  const birthDate = new Date(birthDateString);

  let age = today.getFullYear() - birthDate.getFullYear();

  const m = today.getMonth() - birthDate.getMonth();
  if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
    age--;
  }

  return age;
}

</script>

<template>

  <div class="dentist-container">
    <div class="dentists-title">
      <span>DENTIST CARD</span>
    </div>
    <div class="dentist-block">
      <div class="dentist-block-main-info">
        <div class="dentist-block-main-info-text">
          <span>
            <b>{{dentist?.firstName + " " + dentist?.middleName + " " + dentist?.lastName}}</b>
          </span>
        </div>
        <div>
          <img class='dentist-block-main-info-img' :src="toothlessSurgeon" alt="toothlessSurgeon"/>
        </div>
      </div>

      <div class="dentist-decoration-line"/>
      <div class="dentist-block-text-field">
        <span class="dentist-block-text-field-label">Age</span>
        <span class="dentist-block-text-field-info">{{getAge(dentist?.birthDate)}} years</span>
      </div>

      <div class="dentist-decoration-line"></div>
      <div class="dentist-block-text-field">
        <span class="dentist-block-text-field-label">Email</span>
        <span class="dentist-block-text-field-info">{{dentist?.email}}</span>
      </div>

      <div class="dentist-decoration-line"></div>
      <div class="dentist-block-text-field">
        <span class="dentist-block-text-field-label">Specialization</span>
        <span class="dentist-block-text-field-info">{{dentist?.specialization}}</span>
      </div>

      <div class="dentist-decoration-line"/>
      <div class="dentist-block-text-field">
        <span class="dentist-block-text-field-label">Education</span>
        <span class="dentist-block-text-field-info">{{dentist?.education}}</span>
      </div>

      <div class="dentist-decoration-line"/>
      <div class="dentist-block-text-field">
        <span class="dentist-block-text-field-label">Experience</span>
        <span class="dentist-block-text-field-info">{{dentist?.experience}} years</span>
      </div>

      <div class="dentist-decoration-line"/>
      <div class="dentist-block-text-field">
        <span class="dentist-block-text-field-label">About</span>
        <span class="dentist-block-text-field-info">{{dentist?.about}}</span>
      </div>
      <div class="dentist-decoration-line"/>

    </div>

    <div class="home-page-block-title second">
      Dentist timetable
    </div>

    <div class="dentist-block">
      <TimetableComponent
          :dentist-id="dentistId"
          :dentist-name="dentist?.firstName + ' ' + dentist?.middleName + ' ' + dentist?.lastName"
          :dentist-specialization="dentist?.specialization"
          :is-preview="true"
      />
    </div>

    <div class="home-page-block-title second">
      Comments
    </div>

    <CommentBlock
        :dentist-id="dentistId"
    />

  </div>
</template>

<style scoped>

@import "@/assets/css/dentists/dentist-info-page.css";

</style>