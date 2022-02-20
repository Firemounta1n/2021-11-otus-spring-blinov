package ru.otus.homework.service;

public interface CommentsService {

    String addNewCommentToBook();

    String getAllComments();

    String getCommentById();

    String updateCommentById();

    String deleteComment();
}
