package ru.otus.homework.service;

public interface BookService {

    String addNewBook();

    String getAllBooks();

    String getBooksByTitle();

    String getBooksByAuthorFio();

    String getBooksByGenreName();

    String updateBook();

    String deleteBook();
}
