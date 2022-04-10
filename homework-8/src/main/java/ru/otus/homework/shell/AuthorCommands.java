package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.entities.Author;
import ru.otus.homework.repositories.AuthorRepository;
import ru.otus.homework.services.AuthorService;
import ru.otus.homework.services.ScannerService;

@ShellComponent
@RequiredArgsConstructor
public class AuthorCommands {

    private final ScannerService scannerService;

    private final AuthorService authorService;

    private final AuthorRepository authorRepository;

    @ShellMethod(value = "Add new author", key = {"aa", "add_author"})
    public String addAuthor() {
        System.out.println("Введите автора книги");
        val fio = scannerService.getScannerInNext();
        return "Новый автор добавлен :" + System.lineSeparator() + authorService.saveNewAuthor(new Author(fio));
    }

    @ShellMethod(value = "Show all authors", key = {"sa", "show_authors"})
    public String showAllAuthors() {
        return "Список всех авторов :" + System.lineSeparator() + authorRepository.findAll();
    }

    @ShellMethod(value = "Get books by author", key = {"ga", "get_by_author"})
    public String getBooksByAuthor() {
        System.out.println("Введите автора книги");
        val fio = scannerService.getScannerInNext();
        return "Список книг по автору :" + System.lineSeparator() + authorService.getBooksByFio(fio);
    }

    @ShellMethod(value = "Update author", key = {"ua", "update_author"})
    public String updateAuthor() {
        System.out.println("Введите ФИО автора книги");
        val currentFio = scannerService.getScannerInNext();
        System.out.println("Введите новое ФИО автора книги");
        val newFio = scannerService.getScannerInNext();
        return "Список книг по обновленному автору :" + System.lineSeparator() + authorService.updateAuthor(currentFio, newFio);
    }

    @ShellMethod(value = "Delete author", key = {"dela", "delete_author"})
    public String deleteAuthor() {
        System.out.println("Введите ФИО автора книги");
        val fio = scannerService.getScannerInNext();
        if (authorService.deleteAuthorByFio(fio)) {
            return "Автор успешно удален";
        } else {
            return "Удалить не удалось: У выбранного автора есть книги";
        }
    }
}
