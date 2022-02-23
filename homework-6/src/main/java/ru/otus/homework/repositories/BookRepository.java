package ru.otus.homework.repositories;

import ru.otus.homework.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    long count();

    Book save(Book book);

    Optional<Book> findById(long id);

    List<Book> findAll();

    List<Book> findByTitle(String title);

    Book updateTitleById(long id, String title);

    void deleteById(long id);
}
