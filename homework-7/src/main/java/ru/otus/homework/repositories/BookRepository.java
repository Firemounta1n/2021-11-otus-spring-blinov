package ru.otus.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.entities.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitle(String title);
}
