package ru.otus.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.homework.entities.Genre;
import ru.otus.homework.repositories.BookRepository;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class GenreController {

    private final BookRepository bookRepository;

    @GetMapping("/genres")
    public Flux<Genre> readAll() {
        return bookRepository.findAllGenres();
    }
}
