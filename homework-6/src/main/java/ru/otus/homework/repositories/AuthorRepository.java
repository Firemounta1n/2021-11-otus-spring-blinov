package ru.otus.homework.repositories;

import ru.otus.homework.entities.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Author save(Author author);

    Optional<Author> findById(long id);

    List<Author> findAll();

    Author findByFio(String fio);

    void deleteById(long id);

}
