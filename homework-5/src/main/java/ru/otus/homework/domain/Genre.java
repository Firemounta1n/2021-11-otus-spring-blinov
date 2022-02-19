package ru.otus.homework.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Genre {

    private final Long id;
    private final Long bookId;
    private final String name;

    public Genre(String name) {
        this.id = null;
        this.bookId = null;
        this.name = name;
    }
}
