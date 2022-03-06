package ru.otus.homework.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.entities.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    Author findByFio(String fio);
}
