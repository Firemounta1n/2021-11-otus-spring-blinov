package ru.otus.homework.repositories;

import ru.otus.homework.models.Book;

import java.util.List;

public interface GenreRepository {

    List<Book> findBookByGenreName(String name);

}
