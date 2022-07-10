package ru.otus.homework.utils;

import lombok.experimental.UtilityClass;
import ru.otus.homework.entities.Author;
import ru.otus.homework.rest.dto.AuthorDto;

@UtilityClass
public class AuthorMapper {

    public Author toDomainObject(AuthorDto authorDto) {
        return new Author()
                .setId(authorDto.getId())
                .setFio(authorDto.getFio());
    }

    public static AuthorDto fromDomainObject(Author author) {
        return new AuthorDto(author.getId(), author.getFio());
    }
}
