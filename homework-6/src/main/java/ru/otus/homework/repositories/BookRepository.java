package ru.otus.homework.repositories;

import ru.otus.homework.models.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {

    long count();

    Book save(Book book);

    Optional<Book> findById(long id);

    List<Book> findByTitle(String title);

    List<Book> findAll();

    void updateTitleById(long id, String title);

    void deleteById(long id);
}
