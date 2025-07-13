<script setup>
import toothless from "@/assets/images/toothless/toothless-white-bg-big.png";


import {ref} from "vue";
import {ProfileService} from "@/services/profile.service";
import upload from "@/assets/images/feature/upload_fill_0.svg";

const emit = defineEmits(['close'])

const props = defineProps({
  modalWindowLabel: String
})

const onClose = () => {
  emit('close')
}

const selectedFile = ref(null);
const previewUrl = ref(null);
const error = ref(null);
const success = ref(false);

const handleFileChange = (event) => {
  const file = event.target.files[0];
  error.value = null;
  previewUrl.value = null;

  if (!file) return;

  if (!file.type.startsWith('image/')) {
    error.value = 'Upload only images.';
    return;
  }

  if (file.size > 10 * 1024 * 1024) {
    error.value = 'Image is very big!';
    return;
  }

  selectedFile.value = file;
  previewUrl.value = URL.createObjectURL(file);
};

const uploadImage = async () => {
  if (!selectedFile.value) {
    error.value = 'File not found';
    return;
  }

  const formData = new FormData();
  formData.append('file', selectedFile.value); // 👈 название 'file' важно!

  try {
    const response = await ProfileService.updateProfileAvatar(formData);
    success.value = true;

    onClose()
  } catch (err) {
    console.error('File uploading error:', err);
    error.value = 'File uploading error';
  }
};

</script>

<template>

  <div
      class="procedure-modal-window-overlay"
      @click.self="onClose()"
  >

    <div class="procedure-modal-window-field small">

      <div class="procedure-modal-window-item-right">

        <button
            class="procedure-modal-window-close-button"
            @click="onClose()"
        >
          ×
        </button>

      </div>

      <div class="procedure-modal-window-field-title">
        <span>{{ modalWindowLabel }}</span>
      </div>

      <div class="procedure-modal-window-center">
        <div v-if="previewUrl">
          <img :src="previewUrl" alt="Preview" style="max-width: 200px; margin-top: 10px;" />
        </div>
        <div v-else>

            <img :src="toothless" alt="Preview" style="max-width: 200px; margin-top: 10px;" />

        </div>
        <label class="file-upload-button">
          <img class="profile-add-button-img" :src="upload" alt="add_box">
          <input type="file" accept="image/*" @change="handleFileChange" hidden />
        </label>


        <div v-if="error" style="color: red;">{{ error }}</div>

      </div>

      <div>
        <div class="procedure-modal-window-item-center">
          <button @click="uploadImage()" class="procedure-modal-window-field-button">{{ `Upload` }}</button>
        </div>
      </div>
    </div>
  </div>

</template>

<style scoped>

@import "@/assets/css/procedures/procedure-detailed-window.css";

</style>