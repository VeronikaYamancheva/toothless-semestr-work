package ru.itis.vhsroni.comment.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.vhsroni.comment.data.entity.Comment;
import ru.itis.vhsroni.comment.repository.CommentJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public class CommentJpaRepositoryImpl implements CommentJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            entityManager.persist(comment);
            return comment;
        } else {
            return entityManager.merge(comment);
        }
    }

    @Override
    public Optional<Comment> findById(UUID id) {
        Comment comment = entityManager.find(Comment.class, id);
        return Optional.ofNullable(comment);
    }

    @Override
    public List<Comment> findAll() {
        TypedQuery<Comment> query = entityManager.createQuery("SELECT c FROM Comment c", Comment.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        Comment comment = entityManager.find(Comment.class, id);
        if (comment != null) {
            entityManager.remove(comment);
        }
    }

    @Transactional
    @Override
    public void delete(Comment comment) {
        entityManager.remove(entityManager.contains(comment) ? comment : entityManager.merge(comment));
    }

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(c) FROM Comment c", Long.class);
        return query.getSingleResult();
    }

    @Override
    public boolean existsById(UUID id) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(c) FROM Comment c WHERE c.id = :id", Long.class);
        query.setParameter("id", id);
        return query.getSingleResult() > 0;
    }

    @Override
    public List<Comment> findLatestCommentsLimited(int limit) {
        TypedQuery<Comment> query = entityManager.createQuery("""
            SELECT c FROM Comment c
            WHERE c.parent IS NULL AND c.dentist IS NULL
            ORDER BY c.dateTime DESC
            """, Comment.class);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public List<Comment> findLatestDentistCommentsLimited(int limit, UUID dentistId) {
        TypedQuery<Comment> query = entityManager.createQuery("""
            SELECT c FROM Comment c
            WHERE c.parent IS NULL AND c.dentist.id = :dentistId
            ORDER BY c.dateTime DESC
            """, Comment.class);
        query.setParameter("dentistId", dentistId);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public List<Comment> findAllRepliesByCommentId(UUID commentId) {
        TypedQuery<Comment> query = entityManager.createQuery("""
            SELECT c FROM Comment c
            WHERE c.parent.id = :commentId
            ORDER BY c.dateTime ASC
            """, Comment.class);
        query.setParameter("commentId", commentId);
        return query.getResultList();
    }

    @Override
    public boolean existsByParent_Id(UUID rootCommentId) {
        TypedQuery<Long> query = entityManager.createQuery("""
            SELECT COUNT(c) FROM Comment c
            WHERE c.parent.id = :rootCommentId
            """, Long.class);
        query.setParameter("rootCommentId", rootCommentId);
        return query.getSingleResult() > 0;
    }
}
