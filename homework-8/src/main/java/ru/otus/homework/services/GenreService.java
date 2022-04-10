package ru.otus.homework.services;

import ru.otus.homework.entities.Book;
import ru.otus.homework.entities.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    List<Book> getBooksByGenreName(String name);

    List<Genre> getAllGenres();

    Optional<Genre> getGenreById(String id);

    Optional<Genre> getGenreByName(String name);

    Genre saveNewGenre(Genre genre);

    Optional<Genre> updateGenre(String currentName, String newName);

    Optional<Genre> addBook(Genre genre, Book book);

    void deleteGenreById(String id);

    boolean deleteGenreByName(String genreName);

    void deleteBookFromGenre(Book book);

    void updateGenreBook(Book book);

    Genre findAndAddBookElseCreateNewGenre(String name, Book book);
}