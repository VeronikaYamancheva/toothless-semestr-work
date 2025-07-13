package ru.itis.vhsroni.comment.repository;

import ru.itis.vhsroni.comment.data.entity.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentJpaRepository {
    Comment save(Comment comment);
    Optional<Comment> findById(UUID id);
    List<Comment> findAll();
    void deleteById(UUID id);
    void delete(Comment comment);
    long count();
    boolean existsById(UUID id);

    List<Comment> findLatestCommentsLimited(int limit);
    List<Comment> findLatestDentistCommentsLimited(int limit, UUID dentistId);
    List<Comment> findAllRepliesByCommentId(UUID commentId);
    boolean existsByParent_Id(UUID rootCommentId);
}
