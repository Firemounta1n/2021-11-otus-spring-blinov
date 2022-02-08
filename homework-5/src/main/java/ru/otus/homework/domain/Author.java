package ru.otus.homework.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Author {
    private final Long id;
    private final Long bookId;
    private final String fio;

    public Author(String fio) {
        this.id = null;
        this.bookId = null;
        this.fio = fio;
    }
}
