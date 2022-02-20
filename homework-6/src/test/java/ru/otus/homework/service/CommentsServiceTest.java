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
import ru.otus.homework.repositories.CommentsRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayName("Service для работы с комментариями должен")
@ExtendWith(MockitoExtension.class)
public class CommentsServiceTest {
    private static final long ID_1_HOLDER = 1L;
    private static final String COMMENT_TEXT = "Good!";
    private static final String SECOND_COMMENT_TEXT = "Bad!";
    private static final String NEW_COMMENT_ADDED = "Новый комментарий добавлен к книге";

    private CommentsService commentsService;

    @Mock
    private CommentsRepository commentsRepository;

    @Mock
    private ScannerService scannerService;

    @BeforeEach
    void setUp() {
        commentsService = new CommentsServiceImpl(commentsRepository, scannerService);
    }

    @DisplayName("возвращать строку содержащую текст об успешном обновлении")
    @Test
    void addNewCommentToBookTest() {
        val expectedComment = new Comment()
                .setText(COMMENT_TEXT);

        when(scannerService.getScannerInNext())
                .thenReturn(String.valueOf(ID_1_HOLDER))
                .thenReturn(COMMENT_TEXT);
        when(commentsRepository.save(ID_1_HOLDER, expectedComment)).thenReturn(1);

        val actual = commentsService.addNewCommentToBook();

        assertTrue(actual.contains(NEW_COMMENT_ADDED));
    }

    @DisplayName("возвращать строку содержащую все комментарии ")
    @Test
    void getAllCommentsTest() {
        val expectedComments = Lists.list(
                new Comment().setText(COMMENT_TEXT),
                new Comment().setText(SECOND_COMMENT_TEXT)
        );

        when(commentsRepository.findAll())
                .thenReturn(expectedComments);

        val comments = commentsService.getAllComments();

        assertThat(comments).isNotEmpty()
                .matches(c -> c.contains(COMMENT_TEXT))
                .matches(c -> c.contains(SECOND_COMMENT_TEXT));
    }

    @DisplayName("возвращать строку содержащую найденный по id комментарий ")
    @Test
    void getCommentByIdTest() {
        val expectedComment = new Comment()
                .setText(COMMENT_TEXT);

        when(scannerService.getScannerInNext())
                .thenReturn(String.valueOf(ID_1_HOLDER));
        when(commentsRepository.findById(eq(ID_1_HOLDER)))
                .thenReturn(Optional.of(expectedComment));

        val comment = commentsService.getCommentById();

        assertTrue(comment.contains(COMMENT_TEXT));
    }

    @DisplayName("возвращать строку содержащую id обновленного комментария ")
    @Test
    void updateCommentTextTest() {
        when(scannerService.getScannerInNext())
                .thenReturn(String.valueOf(ID_1_HOLDER))
                .thenReturn(SECOND_COMMENT_TEXT);

        val actual = commentsService.updateCommentById();

        verify(commentsRepository, times(1))
                .updateTextById(eq(ID_1_HOLDER), eq(SECOND_COMMENT_TEXT));

        assertTrue(actual.contains(String.valueOf(ID_1_HOLDER)));
    }

    @DisplayName("возвращать строку содержащую id удаленного комментария ")
    @Test
    void deleteCommentTest() {
        when(scannerService.getScannerInNext())
                .thenReturn(String.valueOf(ID_1_HOLDER));

        val actual = commentsService.deleteComment();

        verify(commentsRepository, times(1))
                .deleteById(eq(ID_1_HOLDER));

        assertTrue(actual.contains(String.valueOf(ID_1_HOLDER)));
    }

}
