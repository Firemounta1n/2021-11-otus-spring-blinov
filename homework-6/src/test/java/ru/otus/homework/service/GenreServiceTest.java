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
import ru.otus.homework.repositories.GenreRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@DisplayName("Service для работы с жанрами должен")
@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {
    private static final String BOOK_TITLE = "Voina i mir";
    private static final String AUTHOR_FIO = "L.N. Tolstoy";
    private static final String GENRE_NAME = "Epic novel";
    private static final String COMMENT_TEXT = "Good!";

    private GenreService genreService;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private ScannerService scannerService;

    @BeforeEach
    void setUp() {
        genreService = new GenreServiceImpl(genreRepository, scannerService);
    }

    @DisplayName("возвращать строку содержащую все книги по жанру ")
    @Test
    void getBooksByGenreTest() {
        val expectedBooks = Lists.list(
                new Book()
                        .setTitle(BOOK_TITLE)
                        .setAuthor(new Author().setFio(AUTHOR_FIO))
                        .setGenre(new Genre().setName(GENRE_NAME))
                        .setComments(List.of(new Comment().setText(COMMENT_TEXT)))
        );

        when(scannerService.getScannerInNext())
                .thenReturn(GENRE_NAME);
        when(genreRepository.findBookByGenreName(eq(GENRE_NAME)))
                .thenReturn(expectedBooks);

        val books = genreService.getBooksByGenreName();

        assertThat(books).isNotNull()
                .contains(BOOK_TITLE)
                .contains(AUTHOR_FIO)
                .contains(GENRE_NAME);
    }

}
