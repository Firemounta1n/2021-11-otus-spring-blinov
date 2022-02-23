package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.repositories.GenreRepositoryJpa;

@ShellComponent
@RequiredArgsConstructor
public class GenreCommands {

    private final GenreRepositoryJpa genreRepositoryJpa;

    @ShellMethod(value = "Show all genres", key = {"sg", "show_genres"})
    public String showAllGenres() {
        return "Список всех жанров :" + System.lineSeparator() + genreRepositoryJpa.findAll();
    }

}
