package ru.itis.vhsroni.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.vhsroni.auth.data.principal.UnifiedAuthPrincipal;
import ru.itis.vhsroni.comment.api.CommentApi;
import ru.itis.vhsroni.comment.data.dto.request.CreateCommentRequest;
import ru.itis.vhsroni.comment.data.dto.response.CommentResponse;
import ru.itis.vhsroni.comment.data.dto.response.CreateCommentResponse;
import ru.itis.vhsroni.comment.service.CommentService;
import ru.itis.vhsroni.config.property.AppConfigProperties;
import ru.itis.vhsroni.error.dto.OperationResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CommentController implements CommentApi {

    private final CommentService commentService;

    private final AppConfigProperties appConfig;

    @Override
    public List<CommentResponse> getMainPageLatestComments(int blocksCount) {
        return commentService.getMainPageLatestComments(blocksCount * appConfig.commentsDemoCount());
    }

    @Override
    public List<CommentResponse> getRepliesByCommentId(UUID commentId) {
        return commentService.getCommentReplies(commentId);
    }

    @Override
    public CreateCommentResponse createNewMainPageComment(UnifiedAuthPrincipal authPrincipal, CreateCommentRequest createCommentRequest) {
        UUID commentId = commentService.createMainPageComment(authPrincipal.getUserId(), createCommentRequest);
        return CreateCommentResponse.builder().commentId(commentId).isSuccess(true).build();
    }

    @Override
    public OperationResponse deleteComment(UUID commentId, UnifiedAuthPrincipal authPrincipal) {
        commentService.deleteComment(commentId);
        return OperationResponse.builder().isSuccess(true).build();
    }

    @Override
    public List<CommentResponse> getDentistLatestComments(int blocksCount, UUID dentistId) {
        return commentService.getDentistLatestComments(blocksCount * appConfig.commentsDemoCount(), dentistId);
    }
}
