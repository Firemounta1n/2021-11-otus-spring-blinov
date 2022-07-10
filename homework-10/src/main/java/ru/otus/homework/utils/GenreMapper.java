package ru.otus.homework.utils;

import lombok.experimental.UtilityClass;
import ru.otus.homework.entities.Genre;
import ru.otus.homework.rest.dto.GenreDto;

@UtilityClass
public class GenreMapper {

    public Genre toDomainObject(GenreDto genreDto) {
        return new Genre()
                .setId(genreDto.getId())
                .setName(genreDto.getName());
    }

    public static GenreDto fromDomainObject(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }
}
