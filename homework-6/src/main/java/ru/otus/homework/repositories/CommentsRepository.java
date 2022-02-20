package ru.otus.homework.repositories;

import ru.otus.homework.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentsRepository {

    long count();

    int save(long book_id, Comment comment);

    Optional<Comment> findById(long id);

    List<Comment> findByText(String title);

    List<Comment> findAll();

    void updateTextById(long id, String text);

    void deleteById(long id);
}
