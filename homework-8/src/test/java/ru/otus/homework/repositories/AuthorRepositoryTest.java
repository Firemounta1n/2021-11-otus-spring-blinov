package ru.otus.homework.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с авторами должен")
class AuthorRepositoryTest extends AbstractRepositoryTest {

    private static final String EXISTING_AUTHOR_FIO = "L.N. Tolstoy";

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("возвращать автора по его фио")
    @Test
    void shouldReturnExpectedByGenreName() {
        val author = authorRepository.findByFio(EXISTING_AUTHOR_FIO);

        assertThat(author.getFio()).isEqualTo(EXISTING_AUTHOR_FIO);
    }

}