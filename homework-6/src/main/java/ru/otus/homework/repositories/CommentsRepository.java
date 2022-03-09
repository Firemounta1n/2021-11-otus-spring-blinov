package ru.otus.homework.repositories;

import ru.otus.homework.entities.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentsRepository {

    long count();

    Comment save(Comment comment);

    Optional<Comment> findById(long id);

    List<Comment> findAll();

    List<Comment> findByText(String title);

    void deleteById(long id);
}
