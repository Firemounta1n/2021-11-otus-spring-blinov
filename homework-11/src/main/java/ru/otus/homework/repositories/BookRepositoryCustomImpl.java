package ru.otus.homework.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import reactor.core.publisher.Flux;
import ru.otus.homework.entities.Author;
import ru.otus.homework.entities.Book;
import ru.otus.homework.entities.Genre;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final ReactiveMongoOperations reactiveMongoOperations;

    @Override
    public Flux<Author> findAllAuthors() {
        List<AggregationOperation> operations = List.of(
                group("author.id", "author.fio"),
                project().and("id").as("_id")
                        .and("fio").as("fio")
        );

        return reactiveMongoOperations.aggregate(newAggregation(operations), Book.class, Author.class);
    }

    @Override
    public Flux<Genre> findAllGenres() {
        List<AggregationOperation> operations = List.of(
                group("genre.id", "genre.name"),
                project().and("id").as("_id")
                        .and("name").as("name")
        );

        return reactiveMongoOperations.aggregate(newAggregation(operations), Book.class, Genre.class);
    }
}
