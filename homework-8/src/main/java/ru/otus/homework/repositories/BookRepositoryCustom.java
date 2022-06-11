package ru.otus.homework.repositories;

import ru.otus.homework.entities.Book;
import ru.otus.homework.entities.Comment;

import java.util.List;

public interface BookRepositoryCustom {

    List<Book> findByGenreName(String genreName);

    List<Book> findByAuthorFio(String authorFio);

    List<Comment> getBookComments(String bookTitle);
}
