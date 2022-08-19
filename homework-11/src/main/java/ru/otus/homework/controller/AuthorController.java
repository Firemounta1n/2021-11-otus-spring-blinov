package ru.otus.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ru.otus.homework.entities.Author;
import ru.otus.homework.repositories.BookRepository;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AuthorController {

    private final BookRepository bookRepository;

    @GetMapping("/authors")
    public Flux<Author> readAll() {
        return bookRepository.findAllAuthors();
    }

}
