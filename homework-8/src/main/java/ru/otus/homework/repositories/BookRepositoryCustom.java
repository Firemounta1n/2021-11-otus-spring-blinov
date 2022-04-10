package ru.otus.homework.repositories;

import ru.otus.homework.entities.Comment;

import java.util.List;

public interface BookRepositoryCustom {

    List<Comment> getBookComments(String bookTitle);

    void deleteCommentFromBook(Comment comment);
}
