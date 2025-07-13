<script setup>
import toothlessIcon from "@/assets/images/toothless/toothless-original.png";
import CommentInputField from "@/presentations/components/comment/CommentInputField.vue";
import CommentCard from "@/presentations/components/comment/CommentCard.vue";
import {onMounted, ref} from "vue";
import {CommentsService} from "@/services/comments.service";

const comments = ref([]);
const blocksCount = ref(0);

onMounted(async () => {
  try {

    await updateComments()

  } catch (err) {
    console.log("Error in CommentBlock: " + err);
  }
});

const updateComments = ( async () => {
  try {
    blocksCount.value = blocksCount.value + 1
    comments.value = await CommentsService.comments(blocksCount.value);
  }
  catch (err) {
    console.log("Error in CommentBlock updateComments: " + err);
  }
});

const resetComments = async () => {
  try {
    blocksCount.value = 0;
    await updateComments()

  } catch (err) {
    console.log("Error in CommentBlock resetComments: " + err);
  }
}

function dateTimeFormator(value) {
  const [date, time] = value.split('T')
  const [hour, minute] = time.split(':')
  const [year, month, day] = date.split('-')
  return `${hour}:${minute} ${day}.${month}.${year}`
}

</script>

<template>

  <div class="comment-layout">

    <CommentInputField
        :label="`Leave a comment:`"
        :button-label="`Post`"
        @commentSent="resetComments"
    />

    <div class="comments-block">
      <ul>
        <li v-for="comment in comments"
            :key="comment"
        >

          <CommentCard
              :user-icon="toothlessIcon"
              :user-name="comment.author.firstName + ' ' + comment.author.lastName"
              :user-type="`Client`"
              :comment-id="comment.commentId"
              :comment-text="comment.content"
              :timestamp="dateTimeFormator(comment.dateTime)"
          />

        </li>

      </ul>
    </div>

    <div class="comment-bottom-buttons">
      <button
          class="comment-input-component-button"
          @click="updateComments"
      >
        Show more
      </button>
      <button
          class="comment-input-component-button"
          @click="resetComments"
      >
        Hide old
      </button>
    </div>

  </div>


</template>

<style scoped>

@import '@/assets/css/comments/comments-style.css';

</style>