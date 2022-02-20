package ru.otus.homework.repositories;

import lombok.val;
import org.springframework.stereotype.Repository;
import ru.otus.homework.models.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    public AuthorRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Book> findBookByFio(String fio) {
        val query = em.createQuery("select b " +
                        "from Book b " +
                        "join Author a on a.id = b.id " +
                        "where a.fio = :fio",
                Book.class);
        query.setParameter("fio", fio);
        return query.getResultList();
    }
}
