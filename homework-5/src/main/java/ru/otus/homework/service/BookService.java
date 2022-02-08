package ru.otus.homework.service;

public interface BookService {

    String addNewBook();

    String getBooksByTitle();

    String getBooksByAuthorFio();

    String getBooksByGenreName();

    String updateBook();

    String deleteBook();

    String getAllBooks();
}
