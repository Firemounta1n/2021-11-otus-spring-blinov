package ru.otus.homework.services;

import ru.otus.homework.entities.Comment;

import java.util.List;

public interface CommentsService {

    Comment saveComment(Comment comment);

    List<Comment> getAllComments();

    Comment getCommentById(String id);

    void deleteComment(String id);
}
