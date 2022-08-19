package ru.otus.homework.repositories;

import reactor.core.publisher.Flux;
import ru.otus.homework.entities.Author;
import ru.otus.homework.entities.Genre;

public interface BookRepositoryCustom {
    Flux<Author> findAllAuthors();

    Flux<Genre> findAllGenres();
}
