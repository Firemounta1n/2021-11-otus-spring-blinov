package ru.otus.homework.repositories;

import ru.otus.homework.entities.Book;

import java.util.List;

public interface GenreRepositoryCustom {

    List<Book> getGenreBooks(String genreName);

    void deleteBookFromGenre(Book book);

    void updateBookInGenre(Book book);
}