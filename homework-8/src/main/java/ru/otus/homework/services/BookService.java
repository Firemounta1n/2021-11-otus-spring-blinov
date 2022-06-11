package ru.otus.homework.services;

import ru.otus.homework.entities.Book;
import ru.otus.homework.entities.Comment;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book addNewBook(Book book);

    List<Book> getAllBooks();

    Optional<Book> getBookById(String id);

    Optional<Book> getBookByTitle(String title);

    List<Book> getBooksByFio(String fio);

    List<Book> getBooksByGenreName(String name);

    Book addCommentToBook(String title, Comment comment);

    Book updateBookTitle(String currentTitle, String title);

    List<Book> updateBooksAuthorFio(String currentFio, String newFio);

    void deleteCommentFromBook(String title, String commentId);

    void deleteBookByTitle(String title);
}
