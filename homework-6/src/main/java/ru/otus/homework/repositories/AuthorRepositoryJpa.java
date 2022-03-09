package ru.otus.homework.repositories;

import lombok.val;
import org.springframework.stereotype.Component;
import ru.otus.homework.entities.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    public AuthorRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == null || author.getId() <= 0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> findAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public Author findByFio(String fio) {
        val query = em.createQuery("select a " +
                        "from Author a " +
                        "where a.fio = :fio",
                Author.class);
        query.setParameter("fio", fio);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(long id) {
        val author = em.find(Author.class, id);
        Optional.ofNullable(author).ifPresent(em::remove);
    }

}
