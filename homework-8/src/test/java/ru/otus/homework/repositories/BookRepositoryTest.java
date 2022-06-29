package ru.otus.homework.repositories;

import lombok.val;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Репозиторий для работы с книгами должен")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookRepositoryTest extends AbstractRepositoryTest {

    private static final int EXPECTED_BOOKS_COUNT = 5;
    private static final String EXISTING_BOOK_TITLE = "Voina i mir";
    private static final String EXISTING_BOOK_THIRD_TITLE = "Voina i mir 4";
    private static final String EXISTING_BOOK_SECOND_TITLE = "Voina i mir 5";
    private static final String EXISTING_AUTHOR_FIO = "L.N. Tolstoy";
    private static final String EXISTING_AUTHOR_SECOND_FIO = "L.N. Tolstoy 4";
    private static final String EXISTING_GENRE_NAME = "Epic novel";
    private static final String EXISTING_GENRE_SECOND_NAME = "Epic novel 3";
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("возвращать ожидаемое количество книг в БД")
    @Test
    @Order(1)
    void shouldReturnExpectedBookCount() {
        val actualBooksCount = bookRepository.count();

        assertThat(actualBooksCount).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("возвращать ожидаемую книгу по ее названию")
    @Test
    @Order(2)
    void shouldReturnExpectedBookByTitle() {
        String existingAuthorId = authorRepository.findAuthorByFio(EXISTING_AUTHOR_FIO).get().getId();
        String existingGenreId = genreRepository.findGenreByName(EXISTING_GENRE_NAME).get().getId();

        val actualBook = bookRepository.findBookByTitle(EXISTING_BOOK_TITLE);

        assertThat(actualBook).isPresent().get()
                .matches(b -> b.getAuthorId() != null && b.getAuthorId().equals(existingAuthorId), existingAuthorId)
                .matches(b -> b.getGenreId() != null && b.getGenreId().equals(existingGenreId), existingGenreId)
        ;
    }

    @DisplayName("возвращать ожидаемые книги по жанру")
    @Test
    @Order(3)
    void shouldReturnExpectedBooksByGenreName() {
        String existingGenreId = genreRepository.findGenreByName(EXISTING_GENRE_NAME).get().getId();

        val actualBooks = bookRepository.findBooksByGenreId(existingGenreId);

        assertThat(actualBooks).isNotEmpty().hasSize(2)
                .anyMatch(b -> b.getTitle() != null && b.getTitle().equals(EXISTING_BOOK_TITLE))
                .anyMatch(b -> b.getTitle() != null && b.getTitle().equals(EXISTING_BOOK_SECOND_TITLE))
                .allMatch(b -> b.getGenreId() != null && b.getGenreId().equals(existingGenreId), existingGenreId)
        ;
    }

    @DisplayName("возвращать ожидаемые книги по автору")
    @Test
    @Order(4)
    void shouldReturnExpectedBooksByAuthorFio() {
        String existingAuthorSecondId = authorRepository.findAuthorByFio(EXISTING_AUTHOR_SECOND_FIO).get().getId();
        String existingGenreId = genreRepository.findGenreByName(EXISTING_GENRE_NAME).get().getId();
        String existingGenreSecondId = genreRepository.findGenreByName(EXISTING_GENRE_SECOND_NAME).get().getId();

        val actualBooks = bookRepository.findBooksByAuthorId(existingAuthorSecondId);

        assertThat(actualBooks).isNotEmpty().hasSize(2)
                .anyMatch(b -> b.getTitle() != null && b.getTitle().equals(EXISTING_BOOK_THIRD_TITLE))
                .anyMatch(b -> b.getTitle() != null && b.getTitle().equals(EXISTING_BOOK_SECOND_TITLE))
                .anyMatch(b -> b.getAuthorId() != null && b.getAuthorId().equals(existingAuthorSecondId))
                .anyMatch(b -> b.getGenreId() != null && b.getGenreId().equals(existingGenreId))
                .anyMatch(b -> b.getGenreId() != null && b.getGenreId().equals(existingGenreSecondId))
        ;
    }

    @DisplayName("удалять заданную книгу по ее названию")
    @Test
    @Order(5)
    void shouldCorrectDeleteBookById() {
        var book = bookRepository.findBookByTitle(EXISTING_BOOK_TITLE);
        assertThat(book).isPresent();

        bookRepository.deleteById(book.get().getId());

        book = bookRepository.findBookByTitle(EXISTING_BOOK_TITLE);
        assertThat(book).isNotPresent();
    }

}