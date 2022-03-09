package ru.otus.homework.repositories;

import ru.otus.homework.entities.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    Genre save(Genre genre);

    Optional<Genre> findById(long id);

    List<Genre> findAll();

    Genre findByName(String name);

    void deleteById(long id);
}
