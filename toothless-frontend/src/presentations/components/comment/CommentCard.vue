<script setup>

/*global defineProps*/
import CommentInputField from "@/presentations/components/comment/CommentInputField.vue";
import { defineEmits, onMounted, ref} from "vue";
import disabled from "@/assets/images/feature/disabled_fill_1.svg";
import {CommentsService} from "@/services/comments.service";
import {useAuthStore} from "@/store/auth.store";
import {ProfileService} from "@/services/profile.service";
import toothlessIcon from "@/assets/images/toothless/toothless-white-bg-big.png";
import {errorHandler} from "@/error/ErrorHandler";

const data = defineProps ({
  author: String,
  commentId: String,
  commentText: String,
  timestamp: String,
  isReply: Boolean,
  rootAuthor: String,
  dentistId: String
});

const authStore = useAuthStore();
const isReplying = ref (false);
const isAnswersSeeing = ref (false);

const replies = ref([]);
const userId = ref()
const userRole = ref()
const userName = ref()
const userIcon = ref()
const hasAuthorities = ref()

const getReplies = async () => {
  try {
    isAnswersSeeing.value = !isAnswersSeeing.value;
    if (!data.commentId) {
      console.warn("commentId is null or undefined");
      return;
    }

    replies.value = await CommentsService.replies(data.commentId);

    if (replies.value.length === 0) {
      replies.value = null
    }
  } catch (err) {
    console.log("Error in CommentBlock: " + err);
  }
};

function dateTimeFormator(value) {
  const [date, time] = value.split("T")
  const [hour, minute] = time.split(':')
  const [year, month, day] = date.split('-')
  return `${hour}:${minute} ${day}.${month}.${year}`
}

const handleReplySent = async () => {
  isReplying.value = false;
  await getReplies();
};

const emit = defineEmits(['commentDelete']);

const onDeleteComment = async () => {
  try {

    const deleteResponse = await CommentsService.deleteComment(data.commentId);
    emit( "commentDelete" );
  }
  catch(error) {
    console.log("Error in CommentBlock in deleteComment: " + error);
  }

}

const getUser = async () => {

  userId.value = data.author.userId
  userName.value = data.author.firstName + " " + data.author.lastName;
  if (data.author.middleName){
    userName.value = data.author.firstName + " " + data.author.middleName + " " + data.author.lastName;
  }

}

onMounted( async () => {

  await getUser()

  if (authStore.user) {
    const id = authStore.user.userId;

    const roles = authStore.user.roles;

    for (const role of roles) {
      if (role === 'ADMIN' || role === 'MASTER') {
        hasAuthorities.value = true;
      }
    }
    if (id === userId.value) {
      hasAuthorities.value = true;
    }

  }
  await uploadUserAvatar(data.author.userId)
})

const uploadUserAvatar = async (userId) => {
  try {
    const avatarResponse = await ProfileService.getProfileAvatar(userId);
    if (!avatarResponse  || avatarResponse.byteLength === 0 || avatarResponse === 404) {
      userIcon.value = toothlessIcon;
      return;
    }
    const base64 = btoa(
        new Uint8Array(avatarResponse).reduce(
            (data, byte) => data + String.fromCharCode(byte),
            ''
        )
    );

    userIcon.value = `data:image/png;base64,${base64}`;

  } catch (error) {
    console.error('Avatar upload error:', error);

    if (error.response?.status === 404 && error.code === 'ERR_BAD_REQUEST') {
      console.warn('[Avatar] Not found, using default');
      userIcon.value = toothlessIcon;
      return;
    }
  }
};

</script>

<template>

  <div class="comment">

    <div class="comment-field-img">
      <img class="comment-profile-img" :src="userIcon" alt="Profile user icon"/>
    </div>
    <div class="comment-field">
      <div class=" comment-user-info-with-delete-button">
        <div class="comment-user-info">

          <div class="comment-user-info-username">
            {{ userName }}
          </div>
          <div class="comment-user-info-usertype">
            {{ userRole }}
          </div>

        </div>
        <button
            v-if="hasAuthorities"
            @click="onDeleteComment()"
        >
          <img :src="disabled" alt="Disabled button">
        </button>
      </div>
      <div class="comment-text">
        {{ commentText }}
      </div>
      <div class="comment-bottom">
        <div class="comment-bottom-buttons">

          <button
              v-if=" !isReplying"
              class="comment-bottom-button"
              @click="isReplying = !isReplying"
          >
            Reply ⮡
          </button>

          <button
              v-if="isReplying"
              class="comment-bottom-button red"
              @click="isReplying = !isReplying"
          >
            Canceled
          </button>

          <button
              v-if="!isAnswersSeeing"
              class="comment-bottom-button"
              @click="getReplies"
          >
            Show answers
          </button>

          <button
              v-if="isAnswersSeeing"
              class="comment-bottom-button red"
              @click="isAnswersSeeing = !isAnswersSeeing"
          >
            Hide answers
          </button>
        </div>

        <div class="comment-bottom-date">
          {{ timestamp }}
        </div>
      </div>

    </div>
  </div>

  <div
      v-if="!isReply"
      class="reply-block"
  >

    <CommentInputField
        v-if="isReplying"
        :parent-id="commentId"
        :label="`⮡  Reply to ` + userName"
        :button-label="`Reply`"
        :dentist-id="dentistId"
        @commentSent="handleReplySent"

    />

    <div v-if="isAnswersSeeing">

      <div
          v-if="replies"
          class="comment-user-info-reply">
        {{'⮡  Replies to ' + userName}}
      </div>
      <div
          v-else
          class="comment-user-info-reply">
        {{'Replies to ' + userName + ' is not exist'}}
      </div>


      <ul>
        <li v-for="reply in replies"
            :key="reply"
            class="reply-comment-input-block"
        >
          <CommentCard
              :comment-id="reply.commentId"
              :author="reply.author"
              :comment-text="reply.content"
              :timestamp="dateTimeFormator(reply.dateTime)"
              :is-reply="true"
              :root-author="userName"
              :dentist-id="dentistId"
              @commentDelete="getReplies"
          />
        </li>
      </ul>

      <div v-if="replies"
           class="comment-user-info-reply"
      >
        {{'End of replies to ' + userName}}
      </div>

    </div>
  </div>

  <div
      v-if="isReply"
  >
    <CommentInputField
        v-if="isReplying"
        :parent-id="commentId"
        :label="`⮡  Reply to ` + userName"
        :button-label="`Reply`"
        :dentist-id="dentistId"
        @commentSent="handleReplySent"
    />

    <div v-if="isAnswersSeeing">

      <div
          v-if="replies"
          class="comment-user-info-reply"
      >
        {{'⮡  Replies to ' + rootAuthor}}
      </div>
      <div
          v-else
          class="comment-user-info-reply">
        {{'Replies to ' + userName + ' is not exist'}}
      </div>

      <ul>
        <li v-for="reply in replies"
            :key="reply"
            class="reply-comment-input-block"
        >
          <CommentCard
              :comment-id="reply.commentId"
              :author="reply.author"
              :comment-text="reply.content"
              :timestamp="dateTimeFormator(reply.dateTime)"
              :is-reply="true"
              :root-author="userName"
              :dentist-id="dentistId"
              @commentDelete="getReplies"
          />
        </li>
      </ul>


      <div v-if="replies"
           class="comment-user-info-reply"
      >
        {{'End of replies to ' + rootAuthor}}
      </div>

    </div>
  </div>

</template>

<style scoped>

</style>