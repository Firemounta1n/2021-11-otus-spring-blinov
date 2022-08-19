package ru.otus.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.entities.Book;
import ru.otus.homework.repositories.BookRepository;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class BookController {

    private final BookRepository repository;

    @ResponseStatus(CREATED)
    @PostMapping("/book")
    public Mono<Book> create(@RequestBody Book book) {
        return repository.save(book);
    }

    @GetMapping("/book/{id}")
    public Mono<Book> read(@PathVariable("id") String id) {
        return repository.findById(id);
    }

    @GetMapping("/books")
    public Flux<Book> readAll() {
        return repository.findAll();
    }

    @ResponseStatus(ACCEPTED)
    @PutMapping("/book")
    public Mono<Book> update(@RequestBody Book book) {
        return repository.save(book);
    }

    @ResponseStatus(ACCEPTED)
    @DeleteMapping("/book/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return repository.deleteById(id);
    }
}
