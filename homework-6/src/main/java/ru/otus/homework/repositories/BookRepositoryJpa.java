package ru.otus.homework.repositories;

import lombok.val;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    public BookRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public long count() {
        val query = em.createQuery("select count (b) " +
                "from Book b",
                Long.class);
        return query.getSingleResult();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null || book.getId() <= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Optional<Book> findById(long id) {
        val entityGraph = em.getEntityGraph("book-author-genre-entity-graph");
        val query = em.createQuery("select b " +
                        "from Book b " +
                        "join fetch b.comments " +
                        "where b.id = :id",
                Book.class);
        query.setParameter("id", id);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultStream().findFirst();
    }

    @Override
    public List<Book> findByTitle(String title) {
        val entityGraph = em.getEntityGraph("book-author-genre-entity-graph");
        val query = em.createQuery("select b " +
                        "from Book b " +
                        "join fetch b.comments " +
                        "where b.title = :title",
                Book.class);
        query.setParameter("title", title);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public List<Book> findAll() {
        val entityGraph = em.getEntityGraph("book-author-genre-entity-graph");
        val query = em.createQuery("select b " +
                "from Book b " +
                "join fetch b.comments",
                Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public void updateTitleById(long id, String title) {
        val query = em.createQuery("update Book b " +
                "set b.title = :title " +
                "where b.id = :id");
        query.setParameter("title", title);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteById(long id) {
        Book book = em.find(Book.class, id);
        Optional.ofNullable(book).ifPresent(em::remove);
    }
}
