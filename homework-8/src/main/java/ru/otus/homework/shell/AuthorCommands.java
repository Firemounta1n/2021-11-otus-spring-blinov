package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.services.AuthorService;
import ru.otus.homework.services.ScannerService;

@ShellComponent
@RequiredArgsConstructor
public class AuthorCommands {

    private final AuthorService authorService;
    private final ScannerService scannerService;

    @ShellMethod(value = "Show all authors", key = {"sa", "show_authors"})
    public String showAllAuthors() {
        return "Список всех авторов :" + System.lineSeparator() + authorService.getAllAuthors();
    }

    @ShellMethod(value = "Update author fio by id", key = {"updaid", "update_author_fio_by_id"})
    public String updateAuthorFioById() {
        System.out.println("Введите Id автора которое требуется обновить");
        val authorId = scannerService.getScannerInNext();
        System.out.println("Введите новое ФИО автора");
        val newAuthorFio = scannerService.getScannerInNext();
        return "ФИО автора обновлено :" + System.lineSeparator() + authorService.updateAuthorById(authorId, newAuthorFio);
    }

    @ShellMethod(value = "Update author fio by fio", key = {"updafio", "update_author_fio_by_fio"})
    public String updateAuthorFioByFio() {
        System.out.println("Введите ФИО автора которое требуется обновить");
        val authorFio = scannerService.getScannerInNext();
        System.out.println("Введите новое ФИО автора");
        val newAuthorFio = scannerService.getScannerInNext();
        return "ФИО автора обновлено :" + System.lineSeparator() + authorService.updateAuthorByFio(authorFio, newAuthorFio);
    }


}
