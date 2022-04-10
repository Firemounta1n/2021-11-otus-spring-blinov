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
import ru.otus.homework.repositories.CommentsRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DisplayName("Service для работы с комментариями должен")
@ExtendWith(MockitoExtension.class)
public class CommentsServiceTest {
    private static final String ID_1_HOLDER = "1";
    private static final String COMMENT_TEXT = "Good!";
    private static final String SECOND_COMMENT_TEXT = "Bad!";

    private CommentsService commentsService;

    @Mock
    private CommentsRepository commentsRepository;

    @BeforeEach
    void setUp() {
        commentsService = new CommentsServiceImpl(commentsRepository);
    }

    @DisplayName("возвращать сохраненный комментарий")
    @Test
    void addNewCommentTest() {
        val expected = new Comment()
                .setText(COMMENT_TEXT);

        when(commentsRepository.save(eq(expected))).thenReturn(expected);

        val actual = commentsService.saveComment(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("возвращать все комментарии ")
    @Test
    void getAllCommentsTest() {
        val expected = Lists.list(
                new Comment().setText(COMMENT_TEXT),
                new Comment().setText(SECOND_COMMENT_TEXT)
        );

        when(commentsRepository.findAll())
                .thenReturn(expected);

        val actual = commentsService.getAllComments();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("возвращать комментарий по id ")
    @Test
    void getCommentByIdTest() {
        val expected = new Comment()
                .setText(COMMENT_TEXT);

        when(commentsRepository.findById(eq(ID_1_HOLDER)))
                .thenReturn(Optional.of(expected));

        val actual = commentsService.getCommentById(ID_1_HOLDER);

        assertThat(actual).isEqualTo(Optional.of(expected));
    }

    @DisplayName("вызывать метод удаления комментария ")
    @Test
    void deleteCommentTest() {
        commentsService.deleteCommentById(ID_1_HOLDER);

        verify(commentsRepository, times(1))
                .deleteById(eq(ID_1_HOLDER));
    }

}
