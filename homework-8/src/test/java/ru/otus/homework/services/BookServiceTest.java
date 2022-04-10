package ru.otus.homework.services;

import lombok.val;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.entities.Comment;
import ru.otus.homework.repositories.BookRepository;
import ru.otus.homework.entities.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayName("Service для работы с книгами должен")
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    private static final String ID_1_HOLDER = "1";
    private static final String BOOK_TITLE = "Voina i mir";
    private static final String SECOND_BOOK_TITLE = "Sun";
    private static final String COMMENT_TEXT = "Good!";
    private static final String SECOND_COMMENT_TEXT = "Bad!";

    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CommentsService commentsService;

    @Mock
    private AuthorService authorService;

    @Mock
    private GenreService genreService;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookRepository, commentsService, authorService, genreService);
    }

    @DisplayName("возвращать добавленную книгу ")
    @Test
    void addBookTest() {
        val expected = new Book()
                .setTitle(BOOK_TITLE)
                .setComments(List.of(new Comment().setText(COMMENT_TEXT)));

        when(bookRepository.save(expected)).thenReturn(expected);

        val actual = bookService.addNewBook(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("возвращать все книги ")
    @Test
    void getAllBooksTest() {
        val expected = Lists.list(
                new Book()
                        .setTitle(BOOK_TITLE)
                        .setComments(List.of(new Comment().setText(COMMENT_TEXT))),
                new Book()
                        .setTitle(SECOND_BOOK_TITLE)
                        .setComments(List.of(new Comment().setText(SECOND_COMMENT_TEXT)))
        );

        when(bookRepository.findAll())
                .thenReturn(expected);

        val actual = bookService.getAllBooks();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("возвращать все книги по названию ")
    @Test
    void getBookByTitleTest() {
        val expected = Optional.of(
                new Book()
                        .setTitle(BOOK_TITLE)
                        .setComments(List.of(new Comment().setText(COMMENT_TEXT)))
        );

        when(bookRepository.findByTitle(eq(BOOK_TITLE)))
                .thenReturn(expected);

        val actual = bookService.getBookByTitle(BOOK_TITLE);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("добавлять новый комментарий к книге по id")
    @Test
    void shouldSaveNewCommentToBook() {
        val book = new Book().setComments(new ArrayList<>());
        book.getComments().add(new Comment().setText(COMMENT_TEXT));

        val newComment = new Comment().setText(SECOND_COMMENT_TEXT);

        val expected = Lists.list(
                new Comment().setText(COMMENT_TEXT),
                newComment
        );

        when(bookRepository.findByTitle(eq(BOOK_TITLE))).thenReturn(Optional.of(book));

        val actual = bookService.addCommentToBook(BOOK_TITLE, newComment);

        assertThat(actual).isPresent();
        assertThat(actual.get().getComments()).isNotEmpty().hasSize(2)
                .usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("возвращать книгу с обновленным названием ")
    @Test
    void updateBookTitleTest() {
        val found = new Book()
                .setTitle(BOOK_TITLE)
                .setComments(List.of(new Comment().setText(COMMENT_TEXT)));

        val expected = found.setTitle(SECOND_BOOK_TITLE);

        when(bookRepository.findByTitle(eq(BOOK_TITLE))).thenReturn(Optional.of(found));

        when(bookRepository.save(eq(expected)))
                .thenReturn(expected);

        val actual = bookService.updateBookTitle(BOOK_TITLE, SECOND_BOOK_TITLE);

        assertThat(actual).isPresent().get().isEqualTo(expected);
    }

    @DisplayName("вызывать удаление книги")
    @Test
    void deleteBookTest() {
        val found = new Book()
                .setId(ID_1_HOLDER)
                .setTitle(BOOK_TITLE)
                .setComments(List.of(new Comment().setId("1234").setText(COMMENT_TEXT)));

        when(bookRepository.findByTitle(eq(BOOK_TITLE)))
                .thenReturn(Optional.of(found));

        bookService.deleteBookByTitle(BOOK_TITLE);

        verify(authorService, times(1))
                .deleteBookFromAuthor(eq(found));

        verify(genreService, times(1))
                .deleteBookFromGenre(eq(found));

        verify(commentsService, times(1))
                .deleteCommentById(eq("1234"));

        verify(bookRepository, times(1))
                .deleteById(eq(ID_1_HOLDER));
    }

}
