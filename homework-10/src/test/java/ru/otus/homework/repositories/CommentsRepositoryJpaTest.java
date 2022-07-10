package ru.otus.homework.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA для работы с комментариями должно")
@DataJpaTest
class CommentsRepositoryJpaTest {

    private static final int EXPECTED_COMMENTS_COUNT = 5;
    private static final long EXISTING_COMMENT_ID = 1L;
    private static final String EXISTING_COMMENT_TEXT = "Good!";
    private static final String EXISTING_COMMENT_4_TEXT = "Normal";

    @Autowired
    private CommentsRepository commentsRepository;

    @DisplayName("возвращать ожидаемое количество комментариев в БД")
    @Test
    void shouldReturnExpectedCommentCount() {
        val actualBooksCount = commentsRepository.count();

        assertThat(actualBooksCount).isEqualTo(EXPECTED_COMMENTS_COUNT);
    }

    @DisplayName("возвращать ожидаемый комментарий по его id")
    @Test
    void shouldReturnExpectedCommentById() {
        val actualComment = commentsRepository.findById(EXISTING_COMMENT_ID);

        assertThat(actualComment).isPresent().get()
                .matches(c -> c.getText() != null && c.getText().equals(EXISTING_COMMENT_TEXT));
    }

    @DisplayName("возвращать ожидаемые комментарии по их тексту")
    @Test
    void shouldReturnExpectedCommentByText() {
        val actualComment = commentsRepository.findByText(EXISTING_COMMENT_4_TEXT);

        assertThat(actualComment).isNotEmpty().hasSize(2)
                .allMatch(c -> c.getText() != null && c.getText().equals(EXISTING_COMMENT_4_TEXT));
    }

    @DisplayName("удалять заданный комментарий по его id")
    @Test
    void shouldCorrectDeleteCommentById() {
        var comment = commentsRepository.findById(EXISTING_COMMENT_ID);
        assertThat(comment).isPresent();

        commentsRepository.deleteById(EXISTING_COMMENT_ID);

        comment = commentsRepository.findById(EXISTING_COMMENT_ID);
        assertThat(comment).isNotPresent();
    }

    @DisplayName("возвращать ожидаемый список комментариев")
    @Test
    void shouldReturnExpectedBooksList() {
        val comments = commentsRepository.findAll();
        assertThat(comments).isNotNull().hasSize(EXPECTED_COMMENTS_COUNT)
                .allMatch(c -> !c.getText().equals(""));
    }

}