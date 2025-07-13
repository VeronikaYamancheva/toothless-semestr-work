<script setup>

/*global defineProps*/

import {ref, watch, onMounted, defineEmits, computed, inject} from 'vue';
import {CommentsService} from "@/services/comments.service";

const data = defineProps ({
  label: String,
  parentId: String,
  dentistId: String,
  buttonLabel: String,
})

const alert = inject('alert')

const textareaInput = ref('');

const emit = defineEmits(['update:modelValue', 'commentSent']);

const textarea = ref('');

const onInput = (e) => {
  emit('update:modelValue', e.target.value);

};

const onSubmit = async () => {
  try {
    const length = textareaInput.value.trim().length;

    if (length === 0) {

      alert('Comment cannot be empty.');
      textareaInput.value = '';
      return;
    }

    if (length > 1000) {
      alert('The length of comment cannot exceed 1000 characters.');
      return;
    }
    const response = await CommentsService.sendComment(textareaInput.value, data.dentistId, data.parentId);
    textareaInput.value = '';
    emit('commentSent');

  } catch (error) {
    console.log("Error in CommentInputField sendComment: "+ error)
  }
};

onMounted(() => {

});

watch(() => textareaInput, () => {

});

</script>

<template>

  <div class="comment-layout-title">
    {{label}}
  </div>
  <div class="comment-input-component">

    <textarea
        ref="textarea"
        class="comment-input-field"
        v-model="textareaInput"
        @input="onInput"
        placeholder="Type text..."
    />

    <button
        class="comment-input-component-button"
        type="button"
        @click="onSubmit"
    >
      {{buttonLabel}}
    </button>

  </div>

  <div class="comment-decoration-line"/>

</template>

<style scoped>

@import '@/assets/css/comments/comments-style.css';

</style>