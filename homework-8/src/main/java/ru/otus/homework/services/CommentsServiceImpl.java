package ru.otus.homework.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.entities.Comment;
import ru.otus.homework.repositories.CommentsRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;

    @Override
    public List<Comment> getAllComments() {
        return commentsRepository.findAll();
    }

    @Override
    public Optional<Comment> getCommentById(String id) {
        return commentsRepository.findById(id);
    }

    @Override
    public Comment saveComment(Comment comment) {
        return commentsRepository.save(comment);
    }

    @Override
    public void deleteCommentById(String id) {
        commentsRepository.deleteById(id);
    }
}
