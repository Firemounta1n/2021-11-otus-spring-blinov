package ru.otus.homework.dao;

import ru.otus.homework.domain.Book;
import java.util.List;

public interface BookDao {

    int count();

    void insert(Book book);

    Book getByBookId(long id);

    List<Book> getAllByBookTitle(String title);

    List<Book> getAllByAuthorFio(String fio);

    List<Book> getAllByGenreName(String name);

    List<Book> getAll();

    void updateByBookId(Book book, long id);

    void deleteById(long id);
}
