package ru.otus.homework.services;

import ru.otus.homework.dto.BookDto;
import ru.otus.homework.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book addNewBook(BookDto bookDto);

    List<BookDto> getAllBooks();

    Optional<BookDto> getBookByTitle(String title);

    List<BookDto> getBooksByFio(String fio);

    List<BookDto> getBooksByGenreName(String name);

    Book updateBookTitle(String currentTitle, String title);

    void deleteBookByTitle(String title);
}
