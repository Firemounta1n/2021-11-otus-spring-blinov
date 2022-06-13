package ru.otus.homework.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.entities.Author;

import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    Optional<Author> findByFio(String fio);
}
