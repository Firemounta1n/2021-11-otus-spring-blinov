package ru.otus.homework.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.entities.Genre;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    Genre findByName(String name);
}
