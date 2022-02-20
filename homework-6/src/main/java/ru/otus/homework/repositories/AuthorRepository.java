package ru.otus.homework.repositories;

import ru.otus.homework.models.Book;

import java.util.List;

public interface AuthorRepository {

    List<Book> findBookByFio(String fio);

}
