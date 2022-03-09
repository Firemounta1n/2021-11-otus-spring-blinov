package ru.otus.homework.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("JPA для работы с книгами должно")
@DataJpaTest
class BookRepositoryJpaTest {

    private static final int EXPECTED_BOOKS_COUNT = 4;
    private static final long EXISTING_BOOK_ID = 1L;
    private static final String EXISTING_BOOK_TITLE = "Voina i mir";
    private static final String EXISTING_AUTHOR_FIO = "L.N. Tolstoy";
    private static final String EXISTING_GENRE_NAME = "Epic novel";
    private static final String EXISTING_COMMENT_TEXT = "Good!";
    private static final String EXISTING_COMMENT_2_TEXT = "Awesome!";

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("возвращать ожидаемое количество книг в БД")
    @Test
    void shouldReturnExpectedBookCount() {
        val actualBooksCount = bookRepository.count();

        assertThat(actualBooksCount).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("возвращать ожидаемую книгу по ее id")
    @Test
    void shouldReturnExpectedBookById() {
        val actualBook = bookRepository.findById(EXISTING_BOOK_ID);

        assertThat(actualBook).isPresent().get()
                .matches(b -> b.getAuthor() != null && b.getAuthor().getFio().equals(EXISTING_AUTHOR_FIO))
                .matches(b -> b.getGenre() != null && b.getGenre().getName().equals(EXISTING_GENRE_NAME))
                .matches(b -> b.getComments().size() == 2
                        && b.getComments().get(0).getText().equals(EXISTING_COMMENT_TEXT)
                        && b.getComments().get(1).getText().equals(EXISTING_COMMENT_2_TEXT));

    }

    @DisplayName("возвращать ожидаемую книгу по ее названию")
    @Test
    void shouldReturnExpectedBookByTitle() {
        val actualBook = bookRepository.findByTitle(EXISTING_BOOK_TITLE);

        assertThat(actualBook).isNotEmpty().hasSize(1)
                .anyMatch(b -> b.getAuthor() != null && b.getAuthor().getFio().equals(EXISTING_AUTHOR_FIO))
                .anyMatch(b -> b.getGenre() != null && b.getGenre().getName().equals(EXISTING_GENRE_NAME))
                .anyMatch(b -> b.getComments().size() == 2
                        && b.getComments().get(0).getText().equals(EXISTING_COMMENT_TEXT)
                        && b.getComments().get(1).getText().equals(EXISTING_COMMENT_2_TEXT));
    }

    @DisplayName("удалять заданную книгу по ее id")
    @Test
    void shouldCorrectDeleteBookById() {
        var book = bookRepository.findById(EXISTING_BOOK_ID);

        bookRepository.deleteById(EXISTING_BOOK_ID);

        book = bookRepository.findById(EXISTING_BOOK_ID);
        assertThat(book).isNotPresent();
    }

    @DisplayName("возвращать ожидаемый список книг с оптимизированным кол-вом запросов к БД")
    @Test
    void shouldReturnExpectedBooksList() {
        val books = bookRepository.findAll();

        assertAll(
                () -> assertThat(books).isNotNull().hasSize(EXPECTED_BOOKS_COUNT)
                        .allMatch(b -> !b.getTitle().equals(""))
                        .allMatch(b -> b.getAuthor() != null && !(b.getAuthor().getFio().equals("")))
                        .allMatch(b -> b.getGenre() != null && !(b.getGenre().getName().equals("")))
                        .allMatch(b -> b.getComments() != null && b.getComments().size() > 0)
        );
    }

}