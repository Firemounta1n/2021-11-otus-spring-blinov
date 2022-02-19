package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    private static final int EXPECTED_BOOKS_COUNT = 1;
    private static final long EXISTING_BOOK_ID = 1L;
    private static final long EXPECTED_ID = 3L;
    private static final String EXISTING_BOOK_TITLE = "Voina i mir";
    private static final String EXISTING_AUTHOR_FIO = "L.N. Tolstoy";
    private static final String EXISTING_GENRE_NAME = "Epic novel";

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @DisplayName("возвращать ожидаемое количество книг в БД")
    @Test
    void shouldReturnExpectedBookCount() {
        var actualBooksCount = bookDaoJdbc.count();
        assertThat(actualBooksCount).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Book expectedBook = new Book(
                EXISTING_BOOK_ID,
                EXISTING_BOOK_TITLE,
                new Author(EXISTING_BOOK_ID, EXISTING_BOOK_ID, EXISTING_AUTHOR_FIO),
                new Genre(EXISTING_BOOK_ID, EXISTING_BOOK_ID, EXISTING_GENRE_NAME));
        bookDaoJdbc.insert(expectedBook);
        var actualBook = bookDaoJdbc.getAllByBookTitle(expectedBook.getTitle()).get(0);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("добавлять книгу в БД с автоматической подстановкой id")
    @Test
    void shouldInsertBookWithAutoId() {
        var expectedBook = new Book(
                EXISTING_BOOK_TITLE,
                new Author(EXISTING_AUTHOR_FIO),
                new Genre(EXISTING_GENRE_NAME));

        bookDaoJdbc.insert(expectedBook);

        var actualBook = bookDaoJdbc.getAllByBookTitle(expectedBook.getTitle()).get(1);

        assertThat(actualBook.getTitle()).isEqualTo(expectedBook.getTitle());
        assertThat(actualBook.getAuthor().getFio()).isEqualTo(expectedBook.getAuthor().getFio());
        assertThat(actualBook.getGenre().getName()).isEqualTo(expectedBook.getGenre().getName());

        assertThat(actualBook.getId()).isEqualTo(EXPECTED_ID);
        assertThat(actualBook.getAuthor().getId()).isEqualTo(EXPECTED_ID);
        assertThat(actualBook.getAuthor().getBookId()).isEqualTo(EXPECTED_ID);
        assertThat(actualBook.getGenre().getId()).isEqualTo(EXPECTED_ID);
        assertThat(actualBook.getGenre().getBookId()).isEqualTo(EXPECTED_ID);
    }

    @DisplayName("возвращать ожидаемую книгу по ее id")
    @Test
    void shouldReturnExpectedBookById() {
        var expectedBook = new Book(
                EXISTING_BOOK_ID,
                EXISTING_BOOK_TITLE,
                new Author(EXISTING_AUTHOR_FIO),
                new Genre(EXISTING_GENRE_NAME));

        var actualBook = bookDaoJdbc.getByBookId(expectedBook.getId());

        assertThat(actualBook.getTitle()).isEqualTo(expectedBook.getTitle());
        assertThat(actualBook.getAuthor().getFio()).isEqualTo(expectedBook.getAuthor().getFio());
        assertThat(actualBook.getGenre().getName()).isEqualTo(expectedBook.getGenre().getName());
    }

    @DisplayName("удалять заданную книгу по ее id")
    @Test
    void shouldCorrectDeleteBookById() {
        assertThatCode(() -> bookDaoJdbc.getByBookId(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        bookDaoJdbc.deleteById(EXISTING_BOOK_ID);

        assertThatThrownBy(() -> bookDaoJdbc.getByBookId(EXISTING_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedBooksList() {
        Book expectedBook = new Book(
                1L,
                EXISTING_BOOK_TITLE,
                new Author(1L, 1L, EXISTING_AUTHOR_FIO),
                new Genre(1L, 1L, EXISTING_GENRE_NAME));

        List<Book> actualBookList = bookDaoJdbc.getAll();

        assertThat(actualBookList)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedBook);
    }

}