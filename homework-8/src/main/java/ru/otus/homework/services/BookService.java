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

    Optional<Book> addCommentToBook(String title, Comment comment);

    Optional<Book> updateBookTitle(String currentTitle, String title);

    void deleteCommentFromBook(String title, String commentId);

    void deleteCommentByIdFromAllBooks(String id);

    void deleteBookByTitle(String title);
}
