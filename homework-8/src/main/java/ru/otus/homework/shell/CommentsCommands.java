package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.entities.Comment;
import ru.otus.homework.services.CommentsService;
import ru.otus.homework.services.ScannerService;

@ShellComponent
@RequiredArgsConstructor
public class CommentsCommands {

    private final CommentsService commentsService;

    private final ScannerService scannerService;

    @ShellMethod(value = "Show all comments", key = {"sc", "show_comments"})
    public String showAllComments() {
        return "Все комментарии:" + System.lineSeparator() + commentsService.getAllComments().toString();
    }

    @ShellMethod(value = "Get comment by id", key = {"gci", "get_comment_by_id"})
    public String getCommentById() {
        System.out.println("Введите id комментария");
        val id = scannerService.getScannerInNext();
        return "Найденный комментарий:" + System.lineSeparator() + commentsService.getCommentById(id);
    }

    @ShellMethod(value = "Update comment text by id", key = {"updc", "update_comment_text"})
    public String updateCommentTextById() {
        System.out.println("Введите id комментария для обновления");
        val id = scannerService.getScannerInNext();
        System.out.println("Введите новый текст комментария");
        val text = scannerService.getScannerInNext();
        return "Комментарий обновлен:" + System.lineSeparator() + commentsService.saveComment(
                new Comment().setId(id).setText(text));
    }

    @ShellMethod(value = "Add new comment book", key = {"ac", "add_comment"})
    public String addNewCommentToBook() {
        System.out.println("Введите id книги для которой нужно добавить комментарий");
        val id = scannerService.getScannerInNext();
        System.out.println("Введите новый комментарий");
        val newComment = scannerService.getScannerInNext();
        return "Новый комментарий добавлен к книге :"
                + System.lineSeparator() + commentsService.saveComment(new Comment(id, newComment));
    }

    @ShellMethod(value = "Delete comment by id", key = {"delc", "delete_comment"})
    public String deleteCommentById() {
        System.out.println("Введите id комментария для удаления");
        val id = scannerService.getScannerInNext();
        commentsService.deleteComment(id);
        return "Комментарий c id = " + id + " удален";
    }
}
