package ru.otus.homework.repositories;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.entities.Author;
import ru.otus.homework.entities.Book;
import ru.otus.homework.entities.Comment;
import ru.otus.homework.entities.Genre;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("JPA для работы с книгами должно")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    private static final int EXPECTED_QUERIES_COUNT = 2;
    private static final int EXPECTED_BOOKS_COUNT = 4;
    private static final long EXISTING_BOOK_ID = 1L;
    private static final long EXISTING_AUTHOR_ID = 1L;
    private static final long EXISTING_GENRE_ID = 1L;
    private static final long EXISTING_COMMENT_ID = 1L;
    private static final long EXISTING_COMMENT_2_ID = 2L;
    private static final String EXISTING_BOOK_TITLE = "Voina i mir";
    private static final String EXISTING_BOOK_2_TITLE = "New title";
    private static final String EXISTING_AUTHOR_FIO = "L.N. Tolstoy";
    private static final String EXISTING_GENRE_NAME = "Epic novel";
    private static final String EXISTING_COMMENT_TEXT = "Good!";
    private static final String EXISTING_COMMENT_2_TEXT = "Awesome!";

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("возвращать ожидаемое количество книг в БД")
    @Test
    void shouldReturnExpectedBookCount() {
        val actualBooksCount = bookRepositoryJpa.count();

        assertThat(actualBooksCount).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldSaveBook() {
        val expectedBook = new Book(
                EXISTING_BOOK_ID,
                EXISTING_BOOK_TITLE,
                new Author()
                        .setId(EXISTING_AUTHOR_ID)
                        .setFio(EXISTING_AUTHOR_FIO),
                new Genre()
                        .setId(EXISTING_GENRE_ID)
                        .setName(EXISTING_GENRE_NAME),
                Arrays.asList(
                        new Comment()
                                .setId(EXISTING_COMMENT_ID)
                                .setText(EXISTING_COMMENT_TEXT),
                        new Comment()
                                .setId(EXISTING_COMMENT_2_ID)
                                .setText(EXISTING_COMMENT_2_TEXT)
                )
        );

        bookRepositoryJpa.save(expectedBook);

        val actualBook = em.find(Book.class, expectedBook.getId());

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("возвращать ожидаемую книгу по ее id")
    @Test
    void shouldReturnExpectedBookById() {
        val actualBook = bookRepositoryJpa.findById(EXISTING_BOOK_ID);

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
        val actualBook = bookRepositoryJpa.findByTitle(EXISTING_BOOK_TITLE);

        assertThat(actualBook).isNotEmpty().hasSize(1)
                .anyMatch(b -> b.getAuthor() != null && b.getAuthor().getFio().equals(EXISTING_AUTHOR_FIO))
                .anyMatch(b -> b.getGenre() != null && b.getGenre().getName().equals(EXISTING_GENRE_NAME))
                .anyMatch(b -> b.getComments().size() == 2
                        && b.getComments().get(0).getText().equals(EXISTING_COMMENT_TEXT)
                        && b.getComments().get(1).getText().equals(EXISTING_COMMENT_2_TEXT));
    }

    @DisplayName("обновлять название книги по ее id")
    @Test
    void shouldCorrectUpdateBookTitleById() {
        bookRepositoryJpa.updateTitleById(EXISTING_BOOK_ID, EXISTING_BOOK_2_TITLE);

        val actualBook = bookRepositoryJpa.findById(EXISTING_BOOK_ID);

        assertThat(actualBook).isPresent().get()
                .matches(b -> b.getTitle().equals(EXISTING_BOOK_2_TITLE));
    }

    @DisplayName("удалять заданную книгу по ее id")
    @Test
    void shouldCorrectDeleteBookById() {
        var book = bookRepositoryJpa.findById(EXISTING_BOOK_ID);

        bookRepositoryJpa.deleteById(EXISTING_BOOK_ID);

        book = bookRepositoryJpa.findById(EXISTING_BOOK_ID);
        assertThat(book).isNotPresent();
    }

    @DisplayName("возвращать ожидаемый список книг с оптимизированным кол-вом запросов к БД")
    @Test
    void shouldReturnExpectedBooksList() {
        val sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val books = bookRepositoryJpa.findAll();

        assertAll(
                () -> assertThat(books).isNotNull().hasSize(EXPECTED_BOOKS_COUNT)
                        .allMatch(b -> !b.getTitle().equals(""))
                        .allMatch(b -> b.getAuthor() != null && !(b.getAuthor().getFio().equals("")))
                        .allMatch(b -> b.getGenre() != null && !(b.getGenre().getName().equals("")))
                        .allMatch(b -> b.getComments() != null && b.getComments().size() > 0),
                () -> assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT)
        );
    }

}