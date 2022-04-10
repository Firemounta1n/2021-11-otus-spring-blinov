package ru.otus.homework.repositories;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.homework.entities.Author;
import ru.otus.homework.entities.Book;
import ru.otus.homework.entities.Genre;

import java.util.Collections;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@RequiredArgsConstructor
public class GenreRepositoryCustomImpl implements GenreRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Book> getGenreBooks(String genreName) {
        val aggregation = newAggregation(
                match(Criteria.where("name").is(genreName))
                ,unwind("books")
                ,project().andExclude("_id")
                        .and("books.id").as("_id")
                        .and("books.title").as("title")
                        .and("books.comments").as("comments")
        );
        return mongoTemplate.aggregate(aggregation, Author.class, Book.class).getMappedResults();
    }

    @Override
    public void deleteBookFromGenre(Book book) {
        Update update = new Update().pull("books",
                Collections.singletonMap("_id", new ObjectId(book.getId())));
        mongoTemplate.updateMulti(new Query(), update, Genre.class);
    }

    @Override
    public void updateBookInGenre(Book book) {
        Query query = new Query(Criteria.where("books._id").is(new ObjectId(book.getId())));
        Update update = new Update().set("books.$.title", book.getTitle());
        mongoTemplate.updateMulti(query, update, Genre.class);
    }
}