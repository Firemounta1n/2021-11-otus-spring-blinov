package ru.otus.homework.repositories;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.homework.entities.Book;
import ru.otus.homework.entities.Comment;

import java.util.Collections;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Comment> getBookComments(String bookTitle) {
        val aggregation = newAggregation(
                match(Criteria.where("title").is(bookTitle))
                ,unwind("comments")
                ,project().andExclude("_id").and("comments.id").as("_id").and("comments.text").as("text")
        );
        return mongoTemplate.aggregate(aggregation, Book.class, Comment.class).getMappedResults();
    }

    @Override
    public void deleteCommentFromBook(Comment comment) {
        Update update = new Update().pull("comments",
                Collections.singletonMap("_id", new ObjectId(comment.getId())));
        mongoTemplate.updateMulti(new Query(), update, Book.class);
    }
}
