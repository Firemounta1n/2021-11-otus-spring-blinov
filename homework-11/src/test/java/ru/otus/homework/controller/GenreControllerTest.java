package ru.otus.homework.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.otus.homework.repositories.BookRepository;

@DisplayName("Контроллер для работы с жанрами должен ")
@WebFluxTest
@ContextConfiguration(classes = {GenreController.class})
public class GenreControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookRepository repository;

    @DisplayName("возвращать список жанров")
    @Test
    public void findAllTest() {
        webTestClient.get()
                .uri("/api/genres")
                .exchange()
                .expectStatus()
                .isOk();
    }
}
