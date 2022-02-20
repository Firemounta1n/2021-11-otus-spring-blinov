package ru.otus.homework.repositories;

import lombok.val;
import org.springframework.stereotype.Repository;
import ru.otus.homework.models.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private final EntityManager em;

    public GenreRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Book> findBookByGenreName(String name) {
        val query = em.createQuery("select b " +
                        "from Book b " +
                        "join Genre g on g.id = b.id " +
                        "where g.name = :name",
                Book.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
}
