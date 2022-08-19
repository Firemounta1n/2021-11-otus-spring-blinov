package ru.otus.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.entities.Comment;
import ru.otus.homework.repositories.CommentRepository;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentController {

    private final CommentRepository repository;

    @ResponseStatus(CREATED)
    @PostMapping("/comment")
    public Mono<Comment> add(@RequestBody Comment comment) {
        return repository.save(comment);
    }

    @GetMapping("/book/{bookId}/comments")
    public Flux<Comment> findByBookId(@PathVariable("bookId") String bookId) {
        return repository.findByBookId(bookId);
    }

    @DeleteMapping("/comment/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return repository.deleteById(id);
    }
}
