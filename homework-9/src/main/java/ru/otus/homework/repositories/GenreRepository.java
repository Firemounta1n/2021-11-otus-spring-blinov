package ru.otus.homework.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.entities.Genre;

import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    Optional<Genre> findByName(String name);
}
