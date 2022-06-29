package ru.otus.homework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {

    Optional<Book> findBookByTitle(String title);
    List<Book> findBooksByAuthorId(String authorId);
    List<Book> findBooksByGenreId(String authorId);
}
