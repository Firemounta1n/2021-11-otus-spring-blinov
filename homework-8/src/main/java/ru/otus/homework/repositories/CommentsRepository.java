package ru.otus.homework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.entities.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentsRepository extends MongoRepository<Comment, String> {

    Optional<Comment> findByText(String text);

    List<Comment> findByBookId(String bookId);
}
