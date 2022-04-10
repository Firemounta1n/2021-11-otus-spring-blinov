package ru.otus.homework.services;

import ru.otus.homework.entities.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentsService {

    Comment saveComment(Comment comment);

    List<Comment> getAllComments();

    Optional<Comment> getCommentById(String id);

    void deleteCommentById(String id);
}
