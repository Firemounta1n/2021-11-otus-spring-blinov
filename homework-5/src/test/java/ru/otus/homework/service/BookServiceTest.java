package ru.otus.homework.service;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@DisplayName("Service для работы с книгами должен")
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    private static final long ID_1_HOLDER = 1L;
    private static final String EXISTING_BOOK_TITLE = "Voina i mir";
    private static final String EXISTING_SECOND_BOOK_TITLE = "Sun";
    private static final String EXISTING_AUTHOR_FIO = "L.N. Tolstoy";
    private static final String EXISTING_SECOND_AUTHOR_FIO = "A.E. Kukushkin";
    private static final String EXISTING_GENRE_NAME = "Epic novel";
    private static final String EXISTING_SECOND_GENRE_NAME = "Fantastic";

    private BookService bookService;

    @Mock
    private BookDao bookDao;

    @Mock
    private ScannerService scannerService;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookDao, scannerService);
    }

    @DisplayName("возвращать строку содержащую название добавленной книги ")
    @Test
    void addBookTest() {
        Book book = new Book(EXISTING_BOOK_TITLE,
                new Author (EXISTING_AUTHOR_FIO),
                new Genre(EXISTING_GENRE_NAME));

        Mockito.when(scannerService.getScannerInNext())
                .thenReturn(EXISTING_BOOK_TITLE)
                .thenReturn(EXISTING_AUTHOR_FIO)
                .thenReturn(EXISTING_GENRE_NAME);

        String actual = bookService.addNewBook();

        Mockito.verify(bookDao, times(1)).insert(eq(book));

        Assertions.assertTrue(actual.contains(EXISTING_BOOK_TITLE));
    }

    @DisplayName("возвращать строку содержащую все книги ")
    @Test
    void getAllBooksTest() {
        List<Book> expectedBooks = Lists.list(
                new Book(EXISTING_BOOK_TITLE,
                        new Author(EXISTING_AUTHOR_FIO),
                        new Genre(EXISTING_GENRE_NAME)),
                new Book(EXISTING_SECOND_BOOK_TITLE,
                        new Author(EXISTING_SECOND_AUTHOR_FIO),
                        new Genre(EXISTING_SECOND_GENRE_NAME))
        );

        Mockito.when(bookDao.getAll())
                .thenReturn(expectedBooks);

        String books = bookService.getAllBooks();
        Assertions.assertTrue(books.contains(EXISTING_BOOK_TITLE));
        Assertions.assertTrue(books.contains(EXISTING_AUTHOR_FIO));
        Assertions.assertTrue(books.contains(EXISTING_GENRE_NAME));

        Assertions.assertTrue(books.contains(EXISTING_SECOND_BOOK_TITLE));
        Assertions.assertTrue(books.contains(EXISTING_SECOND_AUTHOR_FIO));
        Assertions.assertTrue(books.contains(EXISTING_SECOND_GENRE_NAME));
    }

    @DisplayName("возвращать строку содержащую все книги по названию ")
    @Test
    void getBooksByTitleTest() {
        List<Book> expectedBooks = Lists.list(
                new Book(EXISTING_BOOK_TITLE,
                        new Author(EXISTING_AUTHOR_FIO),
                        new Genre(EXISTING_GENRE_NAME))
        );

        Mockito.when(scannerService.getScannerInNext())
                .thenReturn(EXISTING_BOOK_TITLE);

        Mockito.when(bookDao.getAllByBookTitle(eq(EXISTING_BOOK_TITLE)))
                .thenReturn(expectedBooks);

        String books = bookService.getBooksByTitle();
        Assertions.assertTrue(books.contains(EXISTING_BOOK_TITLE));
        Assertions.assertTrue(books.contains(EXISTING_AUTHOR_FIO));
        Assertions.assertTrue(books.contains(EXISTING_GENRE_NAME));
    }

    @DisplayName("возвращать строку содержащую все книги по автору ")
    @Test
    void getBooksByAuthorTest() {
        List<Book> expectedBooks = Lists.list(
                new Book(EXISTING_BOOK_TITLE,
                        new Author(EXISTING_AUTHOR_FIO),
                        new Genre(EXISTING_GENRE_NAME))
        );

        Mockito.when(scannerService.getScannerInNext())
                .thenReturn(EXISTING_BOOK_TITLE);

        Mockito.when(bookDao.getAllByAuthorFio(eq(EXISTING_BOOK_TITLE)))
                .thenReturn(expectedBooks);

        String books = bookService.getBooksByAuthorFio();
        Assertions.assertTrue(books.contains(EXISTING_BOOK_TITLE));
        Assertions.assertTrue(books.contains(EXISTING_AUTHOR_FIO));
        Assertions.assertTrue(books.contains(EXISTING_GENRE_NAME));
    }

    @DisplayName("возвращать строку содержащую все книги по жанру ")
    @Test
    void getBooksByGenreTest() {
        List<Book> expectedBooks = Lists.list(
                new Book(EXISTING_BOOK_TITLE,
                        new Author(EXISTING_AUTHOR_FIO),
                        new Genre(EXISTING_GENRE_NAME))
        );

        Mockito.when(scannerService.getScannerInNext())
                .thenReturn(EXISTING_BOOK_TITLE);

        Mockito.when(bookDao.getAllByGenreName(eq(EXISTING_BOOK_TITLE)))
                .thenReturn(expectedBooks);

        String books = bookService.getBooksByGenreName();
        Assertions.assertTrue(books.contains(EXISTING_BOOK_TITLE));
        Assertions.assertTrue(books.contains(EXISTING_AUTHOR_FIO));
        Assertions.assertTrue(books.contains(EXISTING_GENRE_NAME));
    }

    @DisplayName("возвращать строку содержащую id обновленной книги ")
    @Test
    void updateBookTest() {
        Book book = new Book(EXISTING_SECOND_AUTHOR_FIO,
                new Author (EXISTING_SECOND_AUTHOR_FIO),
                new Genre(EXISTING_SECOND_GENRE_NAME));

        Mockito.when(scannerService.getScannerInNext())
                .thenReturn(String.valueOf(ID_1_HOLDER))
                .thenReturn(EXISTING_SECOND_AUTHOR_FIO)
                .thenReturn(EXISTING_SECOND_AUTHOR_FIO)
                .thenReturn(EXISTING_SECOND_GENRE_NAME);

        String actual = bookService.updateBook();

        Mockito.verify(bookDao, times(1)).updateByBookId(eq(book), eq(ID_1_HOLDER));

        Assertions.assertTrue(actual.contains(String.valueOf(ID_1_HOLDER)));
    }


    @DisplayName("возвращать строку содержащую id удаленной книги ")
    @Test
    void deleteBookTest() {
        Mockito.when(scannerService.getScannerInNext())
                .thenReturn(String.valueOf(ID_1_HOLDER));

        String actual = bookService.deleteBook();

        Mockito.verify(bookDao, times(1)).deleteById(eq(ID_1_HOLDER));

        Assertions.assertTrue(actual.contains(String.valueOf(ID_1_HOLDER)));
    }

}
