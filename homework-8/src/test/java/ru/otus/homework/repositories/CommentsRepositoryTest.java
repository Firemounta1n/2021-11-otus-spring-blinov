package ru.otus.homework.repositories;

import lombok.val;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с комментариями должен")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentsRepositoryTest extends AbstractRepositoryTest {

    private static final int EXPECTED_COMMENTS_COUNT = 7;
    private static final String EXISTING_COMMENT_TEXT = "Good!";
    private static final String EXISTING_COMMENT_4_TEXT = "Normal";

    @Autowired
    private CommentsRepository commentsRepository;

    @DisplayName("возвращать ожидаемое количество комментариев в БД")
    @Test
    @Order(1)
    void shouldReturnExpectedCommentCount() {
        val actualBooksCount = commentsRepository.count();
        System.out.println(commentsRepository.findAll());

        assertThat(actualBooksCount).isEqualTo(EXPECTED_COMMENTS_COUNT);
    }

    @DisplayName("возвращать ожидаемый комментарий по его тексту")
    @Test
    @Order(2)
    void shouldReturnExpectedCommentByText() {
        val actualComment = commentsRepository.findByText(EXISTING_COMMENT_4_TEXT).orElse(null);

        assertThat(actualComment).isNotNull().matches(comment -> comment.getText().equals(EXISTING_COMMENT_4_TEXT));
    }

    @DisplayName("возвращать ожидаемый список комментариев")
    @Test
    @Order(3)
    void shouldReturnExpectedBooksList() {
        val comments = commentsRepository.findAll();
        assertThat(comments).isNotNull().hasSize(EXPECTED_COMMENTS_COUNT)
                .allMatch(c -> !c.getText().equals(""));
    }

    @DisplayName("удалять заданный комментарий по его тексту")
    @Test
    @Order(4)
    void shouldCorrectDeleteCommentByText() {
        var comment = commentsRepository.findByText(EXISTING_COMMENT_TEXT);

        comment.ifPresent(c -> commentsRepository.deleteById(c.getId()));

        comment = commentsRepository.findByText(EXISTING_COMMENT_TEXT);
        assertThat(comment).isNotPresent();
    }

}