package ru.otus.homework.repositories;

import lombok.val;
import org.springframework.stereotype.Repository;
import ru.otus.homework.models.Comment;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentsRepositoryJpa implements CommentsRepository {

    @PersistenceContext
    private final EntityManager em;

    public CommentsRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public long count() {
        val query = em.createQuery("select count (c) " +
                "from Comment c",
                Long.class);
        return query.getSingleResult();
    }

    @Override
    public int save(long book_id, Comment comment) {
        return em.createNativeQuery("INSERT INTO Comment (BOOK_ID, TEXT) VALUES (?, ?)")
                .setParameter(1, book_id)
                .setParameter(2, comment.getText())
                .executeUpdate();
    }

    @Override
    public Optional<Comment> findById(long id) {
        val query = em.createQuery("select c " +
                        "from Comment c " +
                        "where c.id = :id",
                Comment.class);
        query.setParameter("id", id);
        return query.getResultStream().findFirst();
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
    public List<Comment> findAll() {
        val query = em.createQuery("select c " +
                "from Comment c ",
                Comment.class);
        return query.getResultList();
    }

    @Override
    public void updateTextById(long id, String text) {
        val query = em.createQuery("update Comment c " +
                "set c.text = :text " +
                "where c.id = :id");
        query.setParameter("text", text);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteById(long id) {
        Comment comment = em.find(Comment.class, id);
        Optional.ofNullable(comment).ifPresent(em::remove);
    }
}
