package ru.otus.homework.service;

public interface BookService {

    String addNewBook();

    String getAllBooks();

    String getBooksByTitle();

    String updateBookTitle();

    String deleteBook();
}
