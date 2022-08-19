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
import ru.otus.homework.entities.Book;
import ru.otus.homework.repositories.BookRepository;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@DisplayName("Контроллер для работы с книгами должен ")
@WebFluxTest
@ContextConfiguration(classes = {BookController.class})
class BookControllerTest {

    private static final String ID = ObjectId.get().toString();

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookRepository repository;


    @DisplayName("возвращать книгу по id")
    @Test
    public void findOneTest() {
        webTestClient.get()
                .uri("/api/book/" + ID)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @DisplayName("возвращать список книг")
    @Test
    public void findAllTest() {
        webTestClient.get()
                .uri("/api/books")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @DisplayName("добавлять книгу")
    @Test
    public void addTest() {
        webTestClient.post()
                .uri("/api/book")
                .bodyValue(new Book())
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @DisplayName("редактировать книгу")
    @Test
    public void updateTest() {
        webTestClient.put()
                .uri("/api/book")
                .bodyValue(new Book())
                .exchange()
                .expectStatus()
                .isAccepted();
    }

    @DisplayName("удалять книгу")
    @Test
    public void deleteTest() {
        given(repository.deleteById(anyString())).willReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/book/" + ID)
                .exchange()
                .expectStatus()
                .isAccepted();
    }

}
