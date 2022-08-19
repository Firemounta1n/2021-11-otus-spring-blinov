package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;
import ru.otus.homework.repositories.BookRepository;

import java.util.Set;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static com.mongodb.assertions.Assertions.assertTrue;

@DisplayName("Репозиторий для работы с книгами должен ")
@DataMongoTest
class BookRepositoryTest {

    private static final Set<String> GENRES = Set.of("Genre1", "Genre2");
    private static final Set<String> AUTHOR_FIOS = Set.of("Author1", "Author2");

    @Autowired
    private BookRepository repository;

    @DisplayName("получать всех авторов")
    @Test
    void shouldFindAllAuthors() {
        StepVerifier
                .create(repository.findAllAuthors())
                .assertNext(author -> {
                    assertNotNull(author.getId());
                    assertTrue(AUTHOR_FIOS.contains(author.getFio()));
                })
                .assertNext(author -> {
                    assertNotNull(author.getId());
                    assertTrue(AUTHOR_FIOS.contains(author.getFio()));
                })
                .expectComplete()
                .verify();
    }

    @DisplayName("получать все жанры")
    @Test
    void shouldFindAllGenres() {
        StepVerifier
                .create(repository.findAllGenres())
                .assertNext(genre -> {
                    assertNotNull(genre.getId());
                    assertTrue(GENRES.contains(genre.getName()));
                })
                .assertNext(genre -> {
                    assertNotNull(genre.getId());
                    assertTrue(GENRES.contains(genre.getName()));
                })
                .expectComplete()
                .verify();
    }
}
