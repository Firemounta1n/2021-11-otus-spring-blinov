package ru.otus.homework.repositories;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.models.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA для работы с комментариями должно")
@DataJpaTest
@Import(CommentsRepositoryJpa.class)
class CommentsRepositoryJpaTest {

    private static final int EXPECTED_COMMENTS_COUNT = 5;
    private static final long EXISTING_BOOK_ID = 1L;
    private static final long EXISTING_COMMENT_ID = 1L;
    private static final long EXISTING_NEW_COMMENT_ID = 6L;
    private static final String NEW_COMMENT_TEXT = "New text";
    private static final String EXISTING_COMMENT_TEXT = "Good!";
    private static final String EXISTING_COMMENT_3_TEXT = "Not Bad!";
    private static final String EXISTING_COMMENT_4_TEXT = "Normal";

    @Autowired
    private CommentsRepositoryJpa commentsRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("возвращать ожидаемое количество комментариев в БД")
    @Test
    void shouldReturnExpectedCommentCount() {
        val actualBooksCount = commentsRepositoryJpa.count();

        assertThat(actualBooksCount).isEqualTo(EXPECTED_COMMENTS_COUNT);
    }

    @DisplayName("добавлять новый комментарий к книге в БД")
    @Test
    void shouldSaveNewCommentToBook() {
        val newComment = new Comment().setText(EXISTING_COMMENT_3_TEXT);

        commentsRepositoryJpa.save(EXISTING_BOOK_ID, newComment);

        val actualComment = em.find(Comment.class, EXISTING_NEW_COMMENT_ID);

        assertThat(actualComment.getText()).isEqualTo(newComment.getText());
    }

    @DisplayName("возвращать ожидаемый комментарий по его id")
    @Test
    void shouldReturnExpectedCommentById() {
        val actualComment = commentsRepositoryJpa.findById(EXISTING_COMMENT_ID);

        assertThat(actualComment).isPresent().get()
                .matches(c -> c.getText() != null && c.getText().equals(EXISTING_COMMENT_TEXT));
    }

    @DisplayName("возвращать ожидаемые комментарии по их тексту")
    @Test
    void shouldReturnExpectedCommentByText() {
        val actualComment = commentsRepositoryJpa.findByText(EXISTING_COMMENT_4_TEXT);

        assertThat(actualComment).isNotEmpty().hasSize(2)
                .allMatch(c -> c.getText() != null && c.getText().equals(EXISTING_COMMENT_4_TEXT));
    }

    @DisplayName("удалять заданный комментарий по его id")
    @Test
    void shouldCorrectDeleteCommentById() {
        var comment = commentsRepositoryJpa.findById(EXISTING_COMMENT_ID);
        assertThat(comment).isPresent();

        commentsRepositoryJpa.deleteById(EXISTING_COMMENT_ID);

        comment = commentsRepositoryJpa.findById(EXISTING_COMMENT_ID);
        assertThat(comment).isNotPresent();
    }

    @DisplayName("обновлять текст комментария по его id")
    @Test
    void shouldCorrectUpdateCommentTextById() {
        commentsRepositoryJpa.updateTextById(EXISTING_COMMENT_ID, NEW_COMMENT_TEXT);

        val actualComment = commentsRepositoryJpa.findById(EXISTING_COMMENT_ID);

        assertThat(actualComment).isPresent().get()
                .matches(c -> c.getText().equals(NEW_COMMENT_TEXT));
    }

    @DisplayName("возвращать ожидаемый список комментариев")
    @Test
    void shouldReturnExpectedBooksList() {
        val sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val comments = commentsRepositoryJpa.findAll();
        assertThat(comments).isNotNull().hasSize(EXPECTED_COMMENTS_COUNT)
                .allMatch(c -> !c.getText().equals(""));
    }

}