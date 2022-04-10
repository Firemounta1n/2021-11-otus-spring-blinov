package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.entities.Genre;
import ru.otus.homework.repositories.GenreRepository;
import ru.otus.homework.services.GenreService;
import ru.otus.homework.services.ScannerService;

@ShellComponent
@RequiredArgsConstructor
public class GenreCommands {

    private final ScannerService scannerService;

    private final GenreService genreService;

    private final GenreRepository genreRepository;

    @ShellMethod(value = "Add new genre", key = {"ag", "add_genre"})
    public String addAGenre() {
        System.out.println("Введите наименование жанра");
        val name = scannerService.getScannerInNext();
        return "Новый жанр добавлен :" + System.lineSeparator() + genreService.saveNewGenre(new Genre(name));
    }

    @ShellMethod(value = "Show all genres", key = {"sg", "show_genres"})
    public String showAllGenres() {
        return "Список всех жанров :" + System.lineSeparator() + genreRepository.findAll();
    }

    @ShellMethod(value = "Get books by genre", key = {"gg", "get_by_genre"})
    public String getBooksByGenre() {
        System.out.println("Введите жанр книги");
        val genreName = scannerService.getScannerInNext();
        return "Список книг по жанру :" + System.lineSeparator() + genreService.getBooksByGenreName(genreName);
    }

    @ShellMethod(value = "Update genre", key = {"ug", "update_genre"})
    public String updateGenre() {
        System.out.println("Введите наименование жанра книги");
        val currentName = scannerService.getScannerInNext();
        System.out.println("Введите новое наименование жанра книги");
        val newName = scannerService.getScannerInNext();
        return "Список книг по обновленному жанру :" + System.lineSeparator() + genreService.updateGenre(currentName, newName);
    }

    @ShellMethod(value = "Delete genre", key = {"delg", "delete_genre"})
    public String deleteGenre() {
        System.out.println("Введите Наименование жанра книги");
        val name = scannerService.getScannerInNext();
        if (genreService.deleteGenreByName(name)) {
            return "Жанр успешно удален";
        } else {
            return "Удалить не удалось: По выбранному жанру есть книги";
        }
    }
}
