package ru.otus.homework.services;

import ru.otus.homework.entities.Author;

import java.util.List;
import java.util.Optional;


public interface AuthorService {

    Optional<Author> updateAuthorByFio(String currentAuthorFio, String newAuthorFio);

    Optional<Author> updateAuthorById(String authorId, String authorFio);

    List<Author> getAllAuthors();

}
