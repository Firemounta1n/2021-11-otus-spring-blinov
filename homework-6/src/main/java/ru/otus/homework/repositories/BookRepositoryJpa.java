package ru.otus.homework.repositories;

import lombok.val;
import org.springframework.stereotype.Component;
import ru.otus.homework.entities.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Component
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    public BookRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public long count() {
        return em.createQuery("select count (b) from Book b", Long.class).getSingleResult();
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
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("select b from Book b ",
                Book.class).getResultList();
    }

    @Override
    public List<Book> findByTitle(String title) {
        val query = em.createQuery("select b " +
                        "from Book b " +
                        "where b.title = :title",
                Book.class);
        query.setParameter("title", title);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        val book = em.find(Book.class, id);
        Optional.ofNullable(book).ifPresent(em::remove);
    }
}
