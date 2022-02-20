package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Comment;
import ru.otus.homework.repositories.CommentsRepository;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;

    private final ScannerService scannerService;

    @Transactional
    @Override
    public String addNewCommentToBook() {
        System.out.println("Введите id книги");
        val id = Long.parseLong(scannerService.getScannerInNext());

        System.out.println("Введите новый комментарий");
        val newComment = scannerService.getScannerInNext();

        commentsRepository.save(id, new Comment().setText(newComment));

        return "Новый комментарий добавлен к книге";
    }

    @Transactional
    @Override
    public String getAllComments() {
        return commentsRepository.findAll().toString();
    }

    @Transactional
    @Override
    public String getCommentById() {
        System.out.println("Введите id комментария");
        val id = Long.parseLong(scannerService.getScannerInNext());

        return commentsRepository.findById(id).toString();
    }

    @Transactional
    @Override
    public String updateCommentById() {
        System.out.println("Введите id комментария для обновления");
        val id = Long.parseLong(scannerService.getScannerInNext());

        System.out.println("Введите новый текст комментария");
        val bookTitle = scannerService.getScannerInNext();

        commentsRepository.updateTextById(id, bookTitle);

        return "Комментарий c id = " + id + " обновлен";
    }

    @Transactional
    @Override
    public String deleteComment() {
        System.out.println("Введите id комментария для удаления");
        val id = Long.parseLong(scannerService.getScannerInNext());

        commentsRepository.deleteById(id);

        return "Комментарий c id = " + id + " удален";
    }
}
