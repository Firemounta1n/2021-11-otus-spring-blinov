package ru.otus.homework.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с жанрами должен")
class GenreRepositoryTest extends AbstractRepositoryTest {
    
    private static final String EXISTING_GENRE_NAME = "Epic novel";

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("возвращать жанр по его наименованию")
    @Test
    void shouldReturnExpectedByGenreName() {
        val genre = genreRepository.findGenreByName(EXISTING_GENRE_NAME).get();

        assertThat(genre.getName()).isEqualTo(EXISTING_GENRE_NAME);
    }

}