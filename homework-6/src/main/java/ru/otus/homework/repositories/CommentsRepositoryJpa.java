package ru.otus.homework.repositories;

import lombok.val;
import org.springframework.stereotype.Component;
import ru.otus.homework.entities.Comment;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Component
public class CommentsRepositoryJpa implements CommentsRepository {

    @PersistenceContext
    private final EntityManager em;

    public CommentsRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public long count() {
        return em.createQuery("select count (c) from Comment c", Long.class).getSingleResult();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null || comment.getId() <= 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public List<Comment> findAll() {
        return em.createQuery("select c from Comment c", Comment.class).getResultList();
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findByText(String text) {
        val query = em.createQuery("select c " +
                        "from Comment c " +
                        "where c.text = :text",
                Comment.class);
        query.setParameter("text", text);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        val comment = em.find(Comment.class, id);
        Optional.ofNullable(comment).ifPresent(em::remove);
    }
}
