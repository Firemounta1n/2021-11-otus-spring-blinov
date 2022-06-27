package ru.otus.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.otus.homework.entities.Author;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthorDto {

    private Long id;

    private String fio;

    public Author toDomainObject() {
        return new Author()
                .setId(id)
                .setFio(fio);
    }

    public static AuthorDto fromDomainObject(Author author) {
        return new AuthorDto(author.getId(), author.getFio());
    }
}
