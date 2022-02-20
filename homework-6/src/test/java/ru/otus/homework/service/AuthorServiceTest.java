package ru.otus.homework.service;

import lombok.val;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Comment;
import ru.otus.homework.models.Genre;
import ru.otus.homework.repositories.AuthorRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@DisplayName("Service для работы с авторами должен")
@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    private static final String BOOK_TITLE = "Voina i mir";
    private static final String AUTHOR_FIO = "L.N. Tolstoy";
    private static final String GENRE_NAME = "Epic novel";
    private static final String COMMENT_TEXT = "Good!";

    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private ScannerService scannerService;

    @BeforeEach
    void setUp() {
        authorService = new AuthorServiceImpl(authorRepository, scannerService);
    }

    @DisplayName("возвращать строку содержащую все книги по автору ")
    @Test
    void getBooksByAuthorTest() {
        val expectedBooks = Lists.list(
                new Book()
                        .setTitle(BOOK_TITLE)
                        .setAuthor(new Author().setFio(AUTHOR_FIO))
                        .setGenre(new Genre().setName(GENRE_NAME))
                        .setComments(List.of(new Comment().setText(COMMENT_TEXT)))
        );

        when(scannerService.getScannerInNext())
                .thenReturn(AUTHOR_FIO);
        when(authorRepository.findBookByFio(eq(AUTHOR_FIO)))
                .thenReturn(expectedBooks);

        val books = authorService.getBooksByAuthorFio();

        assertThat(books).isNotNull()
                .contains(BOOK_TITLE)
                .contains(AUTHOR_FIO)
                .contains(GENRE_NAME);
    }

}
