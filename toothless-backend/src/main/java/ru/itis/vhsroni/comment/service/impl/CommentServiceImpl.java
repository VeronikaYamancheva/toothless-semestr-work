package ru.itis.vhsroni.comment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.vhsroni.auth.data.entity.UserData;
import ru.itis.vhsroni.auth.repository.UserDataRepository;
import ru.itis.vhsroni.comment.data.dto.request.CreateCommentRequest;
import ru.itis.vhsroni.comment.data.dto.response.CommentResponse;
import ru.itis.vhsroni.comment.data.entity.Comment;
import ru.itis.vhsroni.comment.data.mapper.CommentMapper;
import ru.itis.vhsroni.comment.repository.CommentJpaRepository;
import ru.itis.vhsroni.comment.service.CommentService;
import ru.itis.vhsroni.dentist.data.entity.Dentist;
import ru.itis.vhsroni.dentist.repository.DentistRepository;
import ru.itis.vhsroni.error.message.ErrorMessageProperties;
import ru.itis.vhsroni.error.enums.ErrorCode;
import ru.itis.vhsroni.error.exception.InternalException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentJpaRepository commentRepository;
    private final DentistRepository dentistRepository;

    private final UserDataRepository userDataRepository;

    private final CommentMapper commentMapper;

    private final ErrorMessageProperties errorMessage;


    @Override
    public List<CommentResponse> getMainPageLatestComments(int commentsCount) {
        return commentRepository.findLatestCommentsLimited(commentsCount).stream()
                .map(commentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentResponse> getCommentReplies(UUID rootCommentId) {
        return commentRepository.findAllRepliesByCommentId(rootCommentId).stream()
                .map(commentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UUID createMainPageComment(UUID userId, CreateCommentRequest createCommentRequest) {
        UserData client = userDataRepository.findById(userId)
                .orElseThrow(() -> new InternalException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        Comment newComment = Comment.builder()
                .author(client)
                .content(createCommentRequest.content())
                .build();
        if (createCommentRequest.parentId() != null) {
            Comment rootComment = commentRepository.findById(createCommentRequest.parentId()).orElse(null);
            newComment.setParent(rootComment);
        }
        if (createCommentRequest.dentistId() != null) {
            Dentist dentist = dentistRepository.findById(createCommentRequest.dentistId()).orElse(null);
            newComment.setDentist(dentist);
        }
        return commentRepository.save(newComment).getId();
    }

    @Override
    public void deleteComment(UUID commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentResponse> getDentistLatestComments(int commentsCount, UUID dentistId) {
        return commentRepository.findLatestDentistCommentsLimited(commentsCount, dentistId).stream()
                .map(commentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isCommentOwner(UUID commentId, UUID userId) {
        return commentRepository.findById(commentId)
                .map(comment -> comment.getAuthor().getUserId().equals(userId))
                .orElse(false);
    }
}
