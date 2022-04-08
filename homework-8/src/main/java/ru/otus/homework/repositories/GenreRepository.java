package ru.otus.homework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.entities.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {

    Genre findByName(String name);
}
