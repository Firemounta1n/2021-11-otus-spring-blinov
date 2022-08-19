package ru.otus.homework.repository;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import reactor.test.StepVerifier;
import ru.otus.homework.entities.Book;
import ru.otus.homework.repositories.CommentRepository;

import java.util.Set;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static com.mongodb.assertions.Assertions.assertTrue;

@DisplayName("Репозиторий для работы с комментариями должен ")
@DataMongoTest
class CommentRepositoryTest {
    private static final Set<String> NAMES = Set.of("Comment1", "Comment3");

    @Autowired
    private CommentRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @SneakyThrows
    @DisplayName("получать коментарии для книги")
    @Test
    void shouldReturnExpectedBookComments() {
        Book book1 = mongoTemplate.findAll(Book.class).stream()
                .filter(book -> book.getTitle().equals("Book1"))
                .findFirst().orElseThrow(Exception::new);

        StepVerifier
                .create(repository.findByBookId(book1.getId()))
                .assertNext(comment -> {
                    assertNotNull(comment.getId());
                    assertTrue(NAMES.contains(comment.getText()));
                })
                .assertNext(comment -> {
                    assertNotNull(comment.getId());
                    assertTrue(NAMES.contains(comment.getText()));
                })
                .expectComplete()
                .verify();
    }
}