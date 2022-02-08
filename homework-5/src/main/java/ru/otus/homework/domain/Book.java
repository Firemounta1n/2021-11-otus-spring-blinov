package ru.otus.homework.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Book {
    private final Long id;
    private final String title;
    private final Author author;
    private final Genre genre;

    public Book(String title, Author author, Genre genre) {
        this.id = null;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }
}
