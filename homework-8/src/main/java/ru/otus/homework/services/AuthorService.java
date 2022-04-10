package ru.otus.homework.services;

import ru.otus.homework.entities.Author;
import ru.otus.homework.entities.Book;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<Book> getBooksByFio(String fio);

    List<Author> getAllAuthors();

    Optional<Author> getAuthorById(String id);

    Optional<Author> getAuthorByFio(String fio);

    Author saveNewAuthor(Author author);

    Optional<Author> updateAuthor(String currentFio, String newFio);

    Optional<Author> addBook(Author author, Book book);

    void deleteAuthorById(String id);

    boolean deleteAuthorByFio(String authorFio);

    void deleteBookFromAuthor(Book book);

    void updateAuthorBook(Book book);

    Author findAndAddBookElseCreateNewAuthor(String fio, Book book);
}
