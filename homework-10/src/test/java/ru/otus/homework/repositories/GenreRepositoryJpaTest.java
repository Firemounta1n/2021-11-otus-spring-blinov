package ru.otus.homework.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA для работы с жанрами должно")
@DataJpaTest
class GenreRepositoryJpaTest {
    
    private static final String EXISTING_BOOK_TITLE = "Voina i mir";
    private static final String EXISTING_GENRE_NAME = "Epic novel";
    private static final String EXISTING_COMMENT_TEXT = "Good!";
    private static final String EXISTING_COMMENT_2_TEXT = "Awesome!";

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("возвращать ожидаемую книгу по ее жанру")
    @Test
    void shouldReturnExpectedBookByGenres() {

        val genre = genreRepository.findByName(EXISTING_GENRE_NAME);

        val actual = genre.get().getBooks();

        assertThat(actual).isNotEmpty().hasSize(1)
                .anyMatch(b -> b.getTitle() != null && b.getTitle().equals(EXISTING_BOOK_TITLE))
                .anyMatch(b -> b.getGenre() != null && b.getGenre().getName().equals(EXISTING_GENRE_NAME))
                .anyMatch(b -> b.getComments().size() == 2
                        && b.getComments().get(0).getText().equals(EXISTING_COMMENT_TEXT)
                        && b.getComments().get(1).getText().equals(EXISTING_COMMENT_2_TEXT));
    }

}