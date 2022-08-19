package ru.otus.homework.controller;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.otus.homework.entities.Comment;
import ru.otus.homework.repositories.CommentRepository;

import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@DisplayName("Контроллер для работы с комментариями должен ")
@WebFluxTest
@ContextConfiguration(classes = {CommentController.class})
class CommentControllerTest {
    private static final String ID = ObjectId.get().toString();

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CommentRepository repository;

    @DisplayName("возвращать список комментариев для книги ")
    @Test
    public void findByBookIdTest() {
        webTestClient.get()
                .uri(format("/api/book/%s/comments", ObjectId.get().toString()))
                .exchange()
                .expectStatus()
                .isOk();
    }

    @DisplayName("добавлять комментарий")
    @Test
    public void saveTest() {
        webTestClient.post()
                .uri("/api/comment")
                .bodyValue(new Comment())
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @DisplayName("удалять комментарий")
    @Test
    public void deleteTest() {
        given(repository.deleteById(anyString())).willReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/comment/" + ID)
                .exchange()
                .expectStatus()
                .isOk();
    }
}