package ru.otus.homework.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.rest.exceptions.NotFoundException;
import ru.otus.homework.rest.dto.BookDto;
import ru.otus.homework.utils.BookMapper;
import ru.otus.homework.services.BookService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class BookController {

    private final BookService bookService;

    @ResponseStatus(CREATED)
    @PostMapping("/book")
    public void create(@RequestBody BookDto bookDto) {
        bookService.saveBook(BookMapper.toDomainObject(bookDto));
    }

    @GetMapping("/book/{id}")
    public BookDto read(@PathVariable("id") Long id) {
        return bookService.getBookById(id)
                .map(BookMapper::fromDomainObject)
                .orElseThrow(NotFoundException::new);
    }

    @GetMapping("/books")
    public List<BookDto> readAll() {
        return bookService.getAllBooks().stream()
                .map(BookMapper::fromDomainObject)
                .collect(Collectors.toList());
    }

    @ResponseStatus(ACCEPTED)
    @PutMapping("/book")
    public void update(@RequestBody BookDto bookDto) {
        bookService.updateBook(BookMapper.toDomainObject(bookDto));
    }

    @ResponseStatus(ACCEPTED)
    @DeleteMapping("/book/{id}")
    public void delete(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
    }
}
