package ru.otus.homework.services;

import ru.otus.homework.entities.Book;
import ru.otus.homework.entities.Comment;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookService {

    Book updateBook(Book book);

    Book saveBook(Book book);

    List<Book> getAllBooks();

    Optional<Book> getBookById(Long id);

    List<Book> getBooksByTitle(String title);

    Set<Book> getBooksByFio(String fio);

    Set<Book> getBooksByGenreName(String name);

    Book addCommentToBook(long id, Comment comment);

    Book updateBookTitle(long id, String title);

    void deleteBook(long id);
}
