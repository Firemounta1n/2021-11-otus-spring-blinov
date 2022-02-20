package ru.otus.homework.service;

import lombok.val;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.models.Comment;
import ru.otus.homework.repositories.BookRepository;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayName("Service для работы с книгами должен")
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    private static final long ID_1_HOLDER = 1L;
    private static final String BOOK_TITLE = "Voina i mir";
    private static final String SECOND_BOOK_TITLE = "Sun";
    private static final String AUTHOR_FIO = "L.N. Tolstoy";
    private static final String SECOND_AUTHOR_FIO = "A.E. Kukushkin";
    private static final String GENRE_NAME = "Epic novel";
    private static final String SECOND_GENRE_NAME = "Fantastic";
    private static final String COMMENT_TEXT = "Good!";
    private static final String SECOND_COMMENT_TEXT = "Bad!";

    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ScannerService scannerService;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookRepository, scannerService);
    }

    @DisplayName("возвращать строку содержащую название добавленной книги ")
    @Test
    void addBookTest() {
        val expectedBook = new Book()
                .setTitle(BOOK_TITLE)
                .setAuthor(new Author().setFio(AUTHOR_FIO))
                .setGenre(new Genre().setName(GENRE_NAME))
                .setComments(List.of(new Comment().setText(COMMENT_TEXT)));

        when(scannerService.getScannerInNext())
                .thenReturn(BOOK_TITLE)
                .thenReturn(AUTHOR_FIO)
                .thenReturn(GENRE_NAME)
                .thenReturn(COMMENT_TEXT);

        when(bookRepository.save(expectedBook)).thenReturn(expectedBook);

        val actual = bookService.addNewBook();

        assertTrue(actual.contains(BOOK_TITLE));
    }

    @DisplayName("возвращать строку содержащую все книги ")
    @Test
    void getAllBooksTest() {
        val expectedBooks = Lists.list(
                new Book()
                        .setTitle(BOOK_TITLE)
                        .setAuthor(new Author().setFio(AUTHOR_FIO))
                        .setGenre(new Genre().setName(GENRE_NAME))
                        .setComments(List.of(new Comment().setText(COMMENT_TEXT))),
                new Book()
                        .setTitle(SECOND_BOOK_TITLE)
                        .setAuthor(new Author().setFio(SECOND_AUTHOR_FIO))
                        .setGenre(new Genre().setName(SECOND_GENRE_NAME))
                        .setComments(List.of(new Comment().setText(SECOND_COMMENT_TEXT)))
        );

        when(bookRepository.findAll())
                .thenReturn(expectedBooks);

        val books = bookService.getAllBooks();

        assertThat(books).isNotNull()
                .contains(BOOK_TITLE)
                .contains(AUTHOR_FIO)
                .contains(GENRE_NAME)
                .contains(SECOND_BOOK_TITLE)
                .contains(SECOND_AUTHOR_FIO)
                .contains(SECOND_GENRE_NAME);
    }

    @DisplayName("возвращать строку содержащую все книги по названию ")
    @Test
    void getBooksByTitleTest() {
        val expectedBooks = Lists.list(
                new Book()
                        .setTitle(BOOK_TITLE)
                        .setAuthor(new Author().setFio(AUTHOR_FIO))
                        .setGenre(new Genre().setName(GENRE_NAME))
                        .setComments(List.of(new Comment().setText(COMMENT_TEXT)))
        );

        when(scannerService.getScannerInNext())
                .thenReturn(BOOK_TITLE);

        when(bookRepository.findByTitle(eq(BOOK_TITLE)))
                .thenReturn(expectedBooks);

        val books = bookService.getBooksByTitle();

        assertThat(books).isNotNull()
                .contains(BOOK_TITLE)
                .contains(AUTHOR_FIO)
                .contains(GENRE_NAME);
    }


    @DisplayName("возвращать строку содержащую id обновленной книги ")
    @Test
    void updateBookTitleTest() {
        when(scannerService.getScannerInNext())
                .thenReturn(String.valueOf(ID_1_HOLDER))
                .thenReturn(SECOND_BOOK_TITLE);

        val actual = bookService.updateBookTitle();

        verify(bookRepository, times(1))
                .updateTitleById(eq(ID_1_HOLDER), eq(SECOND_BOOK_TITLE));

        assertTrue(actual.contains(String.valueOf(ID_1_HOLDER)));
    }

    @DisplayName("возвращать строку содержащую id удаленной книги ")
    @Test
    void deleteBookTest() {
        when(scannerService.getScannerInNext())
                .thenReturn(String.valueOf(ID_1_HOLDER));

        val actual = bookService.deleteBook();

        verify(bookRepository, times(1))
                .deleteById(eq(ID_1_HOLDER));

        assertTrue(actual.contains(String.valueOf(ID_1_HOLDER)));
    }

}
