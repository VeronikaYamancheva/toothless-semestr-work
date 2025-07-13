<script setup>

import HomePageBlock from "@/presentations/components/home/HomePageBlock.vue";
import toothlessDoctor from "@/assets/images/toothless/toothless-doctor.png"
import toothlessSurgeon from "@/assets/images/toothless/toothless-dentist-surgeon.png";
import toothlessInMask from "@/assets/images/toothless/toothless-in-mask-2.png";

import { DentistsService } from "@/services/dentists.service";
import { ProcedureService } from "@/services/procedure.service";
import { inject, onMounted, ref} from "vue";
import HomePageDentistCard from "@/presentations/components/home/HomePageDentistCard.vue";
import HomePageHorizontalScrollWrapper from "@/presentations/components/home/HomePageHorizontalScrollWrapper.vue";
import { useRouter } from 'vue-router';
import CommentBlock from "@/presentations/components/comment/CommentBlock.vue";

const dentists = ref([]);
const procedures = ref([]);
const router = useRouter();

onMounted(async () => {
  const dentistsServiceResponse = await DentistsService.getDentistsForHomePage();
  const procedureServiceResponse = await ProcedureService.getProceduresForHomePage();

  dentists.value = dentistsServiceResponse;
  procedures.value = procedureServiceResponse;

})

function createFullName(dentist) {
  return `${dentist.firstName} ${dentist.middleName} ${dentist.lastName}`;
}

function redirectTo(path) {
  router.push(path);
}

</script>

<template>

  <div class="home-page-container">
    <div class="home-page-layout">
      <HomePageBlock :block-id="1" :block-title="`Toothless Dental Clinic`">
        <div class="home-page-main-block">
          <img class="home-page-main-block-img" :src="toothlessDoctor" alt="Toothless doctor image."/>
            <span class="home-page-main-block-text" >
              <p class="home-page-main-block-label" align="justify">
                Welcome to the main page our dental clinic!
              </p>
              <p align="justify">
                My name is
                <b style="color: var(--light-primary-color)">
                  Dr. Toothless
                </b>
                and I am a highly qualified specialist in the field of dentistry.
              </p>
              <p align="justify">
                In our dental clinic, my colleagues and I can easily help you with your problems.
                All you have to do is choose the
                <router-link
                    class="home-page-main-block-text-link"
                    to='/procedures'
                >
                  procedure
                </router-link>
                you want to enroll in
                or the
                <router-link
                    class="home-page-main-block-text-link"
                    to='/dentists'
                >
                  dentist
                </router-link>
                you would like to visit.
              </p>
            </span>
        </div>
      </HomePageBlock>

      <HomePageBlock :block-id="2" :block-title="`Procedures`">

        <HomePageHorizontalScrollWrapper>
          <div
              v-for="procedure in procedures"
              :key="procedure"
          >
            <HomePageDentistCard
                :id="procedure.procedureId"
                :procedure-label="procedure.name"
                :profile-photo="toothlessInMask"/>
          </div>
        </HomePageHorizontalScrollWrapper>

        <div class="home-page-block-redirection">
          <div class="home-page-block-redirection-item">
            <button
                class="home-page-block-redirection-btn"
                @click="redirectTo('/procedures')"
            >
              See more...
            </button>
          </div>
        </div>

      </HomePageBlock>

      <HomePageBlock :block-id="3" :block-title="`Dentists`">

        <HomePageHorizontalScrollWrapper>
            <div
                v-for="dentist in dentists"
                :key="dentist"
            >
              <HomePageDentistCard
                  :id="dentist.dentistId"
                  :full-name="createFullName(dentist)"
                  :specialization="dentist.specialization"
                  :profile-photo="toothlessSurgeon"/>
            </div>
        </HomePageHorizontalScrollWrapper>

        <div class="home-page-block-redirection">
          <div class="home-page-block-redirection-item">
            <button
                class="home-page-block-redirection-btn"
                @click="redirectTo('/dentists')"
            >
              See more...
            </button>
          </div>
        </div>

      </HomePageBlock>

      <HomePageBlock :block-id="4" :block-title="`Comments`">
        <CommentBlock/>
      </HomePageBlock>

    </div>
  </div>


</template>

<style scoped>

@import '@/assets/css/home/toothless-home-page.css';

</style>