package ru.otus.homework.repositories;

import ru.otus.homework.entities.Book;

import java.util.List;

public interface AuthorRepositoryCustom {

    List<Book> getAuthorBooks(String authorFio);

    void deleteBookFromAuthor(Book book);

    void updateBookInAuthor(Book book);
}