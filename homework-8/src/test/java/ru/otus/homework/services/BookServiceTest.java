package ru.otus.homework.services;

import lombok.val;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.repositories.AuthorRepository;
import ru.otus.homework.repositories.BookRepository;
import ru.otus.homework.entities.Author;
import ru.otus.homework.entities.Book;
import ru.otus.homework.entities.Genre;
import ru.otus.homework.repositories.CommentsRepository;
import ru.otus.homework.repositories.GenreRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayName("Service для работы с книгами должен")
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    private static final String BOOK_TITLE = "Voina i mir";
    private static final String SECOND_BOOK_TITLE = "Sun";
    private static final String AUTHOR_FIO = "L.N. Tolstoy";
    private static final String SECOND_AUTHOR_FIO = "A.E. Kukushkin";
    private static final String GENRE_NAME = "Epic novel";
    private static final String SECOND_GENRE_NAME = "Fantastic";

    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private CommentsRepository commentsRepository;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookRepository, authorRepository, genreRepository, commentsRepository);
    }

    @DisplayName("возвращать добавленную книгу ")
    @Test
    void addBookTest() {
        val bookDto = new BookDto()
            .setTitle(BOOK_TITLE)
            .setAuthorFio(AUTHOR_FIO)
            .setGenreName(GENRE_NAME);

        val author = new Author()
                .setId("123")
                .setFio(AUTHOR_FIO);

        val genre = new Genre()
                .setId("123")
                .setName(GENRE_NAME);

        val expected = new Book()
                .setTitle(BOOK_TITLE)
                .setAuthorId(author.getId())
                .setGenreId(genre.getId());

        when(authorRepository.save(any())).thenReturn(author);
        when(genreRepository.save(any())).thenReturn(genre);
        when(bookRepository.save(any())).thenReturn(expected);

        val actual = bookService.addNewBook(bookDto);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("возвращать все книги ")
    @Test
    void getAllBooksTest() {
        val books = Lists.list(
                new Book()
                        .setTitle(BOOK_TITLE)
                        .setAuthorId("123")
                        .setGenreId("123"),
                new Book()
                        .setTitle(SECOND_BOOK_TITLE)
                        .setAuthorId("234")
                        .setGenreId("234")
        );

        val author = Optional.of(
                new Author()
                        .setId("123")
                        .setFio(AUTHOR_FIO)
        );

        val genre = Optional.of(
                new Genre()
                        .setId("123")
                        .setName(GENRE_NAME)
        );

        val secondAuthor = Optional.of(
                new Author()
                        .setId("123")
                        .setFio(SECOND_AUTHOR_FIO)
        );

        val secondGenre = Optional.of(
                new Genre()
                        .setId("123")
                        .setName(SECOND_GENRE_NAME)
        );

        val expected = Lists.list(
                new BookDto()
                        .setTitle(BOOK_TITLE)
                        .setAuthorFio(AUTHOR_FIO)
                        .setGenreName(GENRE_NAME),
                new BookDto()
                        .setTitle(SECOND_BOOK_TITLE)
                        .setAuthorFio(SECOND_AUTHOR_FIO)
                        .setGenreName(SECOND_GENRE_NAME)
        );

        when(bookRepository.findAll())
                .thenReturn(books);
        when(authorRepository.findById(eq("123")))
                .thenReturn(author);
        when(genreRepository.findById(eq("123")))
                .thenReturn(genre);
        when(authorRepository.findById(eq("234")))
                .thenReturn(secondAuthor);
        when(genreRepository.findById(eq("234")))
                .thenReturn(secondGenre);

        val actual = bookService.getAllBooks();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("возвращать все книги по названию ")
    @Test
    void getBookByTitleTest() {
        val book = Optional.of(
                new Book()
                        .setTitle(BOOK_TITLE)
                        .setAuthorId("123")
                        .setGenreId("123")
        );

        val author = Optional.of(
                new Author()
                        .setId("123")
                        .setFio(AUTHOR_FIO)
        );

        val genre = Optional.of(
                new Genre()
                        .setId("123")
                        .setName(GENRE_NAME)
        );

        val expected = new BookDto()
                .setTitle(BOOK_TITLE)
                .setAuthorFio(AUTHOR_FIO)
                .setGenreName(GENRE_NAME);

        when(bookRepository.findBookByTitle(eq(BOOK_TITLE)))
                .thenReturn(book);
        when(authorRepository.findById(eq("123")))
                .thenReturn(author);
        when(genreRepository.findById(eq("123")))
                .thenReturn(genre);

        val actual = bookService.getBookByTitle(BOOK_TITLE);

        assertThat(actual).isPresent().get().usingRecursiveComparison().isEqualTo(expected);
    }

}
