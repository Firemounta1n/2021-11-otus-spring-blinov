package ru.otus.homework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.entities.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {

    Author findByFio(String fio);
}
