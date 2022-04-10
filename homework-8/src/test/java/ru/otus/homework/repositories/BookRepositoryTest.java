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
    private static final String EXISTING_COMMENT_TEXT = "Good!";
    private static final String EXISTING_COMMENT_2_TEXT = "Awesome!";
    private static final String EXISTING_COMMENT_3_TEXT = "Norm";

    @Autowired
    private BookRepository bookRepository;

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
        val actualBook = bookRepository.findByTitle(EXISTING_BOOK_TITLE);

        assertThat(actualBook).isPresent().get()
                .matches(b -> b.getComments().size() == 2
                        && b.getComments().get(0).getText().equals(EXISTING_COMMENT_TEXT)
                        && b.getComments().get(1).getText().equals(EXISTING_COMMENT_3_TEXT));
    }

    @DisplayName("возвращать все комментарии по титулу книги")
    @Test
    @Order(5)
    void shouldReturnExpectedCommentsByBookTitle() {
        val actualComments = bookRepository.getBookComments(EXISTING_BOOK_TITLE);

        assertThat(actualComments).isNotEmpty().hasSize(2)
                .anyMatch(c -> c.getText() != null && c.getText().equals(EXISTING_COMMENT_TEXT))
                .anyMatch(c -> c.getText() != null && c.getText().equals(EXISTING_COMMENT_3_TEXT));
    }

    @DisplayName("удалять заданную книгу по ее названию")
    @Test
    @Order(6)
    void shouldCorrectDeleteBookById() {
        var book = bookRepository.findByTitle(EXISTING_BOOK_TITLE);
        assertThat(book).isPresent();

        bookRepository.deleteById(book.get().getId());

        book = bookRepository.findByTitle(EXISTING_BOOK_TITLE);
        assertThat(book).isNotPresent();
    }

}