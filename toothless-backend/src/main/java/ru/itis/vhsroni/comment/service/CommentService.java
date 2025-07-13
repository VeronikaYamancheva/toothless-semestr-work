package ru.itis.vhsroni.comment.service;

import ru.itis.vhsroni.comment.data.dto.request.CreateCommentRequest;
import ru.itis.vhsroni.comment.data.dto.response.CommentResponse;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    List<CommentResponse> getMainPageLatestComments (int commentsCount);

    List<CommentResponse> getCommentReplies(UUID rootCommentId);

    UUID createMainPageComment(UUID authorId, CreateCommentRequest createCommentRequest);

    void deleteComment(UUID commentId);

    List<CommentResponse> getDentistLatestComments(int commentsCount, UUID dentistId);

    boolean isCommentOwner(UUID commentId, UUID userId);
}
