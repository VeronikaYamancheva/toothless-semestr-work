package ru.itis.vhsroni.comment.data.mapper;

import org.springframework.stereotype.Component;
import ru.itis.vhsroni.comment.data.dto.response.CommentResponse;
import ru.itis.vhsroni.comment.data.entity.Comment;
import ru.itis.vhsroni.dentist.data.entity.Dentist;
import ru.itis.vhsroni.dentist.repository.DentistRepository;
import ru.itis.vhsroni.util.dto.UserFullName;

@Component
public class CommentMapper {

    public CommentResponse toResponse(Comment entity) {
        Dentist dentist = entity.getDentist();
        Comment rootComment = entity.getParent();

        CommentResponse.CommentResponseBuilder commentResponseBuilder = CommentResponse.builder()
                .commentId(entity.getId())
                .author(CommentResponse.AuthorResponse.builder()
                        .userId(entity.getAuthor().getUserId())
                        .firstName(entity.getAuthor().getFirstName())
                        .lastName(entity.getAuthor().getLastName())
                        .build())
                .content(entity.getContent())
                .dateTime(entity.getDateTime());

        if (dentist != null) {
            commentResponseBuilder.dentist(UserFullName.builder()
                    .firstName(dentist.getUser().getFirstName())
                    .lastName(dentist.getUser().getLastName())
                    .middleName(dentist.getUser().getMiddleName()).build());
        }

        if (rootComment != null) {
            commentResponseBuilder.rootComment(CommentResponse.RootCommentResponse.builder()
                    .authorFirstName(entity.getParent().getAuthor().getFirstName())
                    .authorLastName(entity.getParent().getAuthor().getFirstName())
                    .build());
        }

        return commentResponseBuilder.build();
    }
}
