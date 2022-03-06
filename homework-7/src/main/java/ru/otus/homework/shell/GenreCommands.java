package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.repositories.GenreRepository;

@ShellComponent
@RequiredArgsConstructor
public class GenreCommands {

    private final GenreRepository genreRepository;

    @ShellMethod(value = "Show all genres", key = {"sg", "show_genres"})
    public String showAllGenres() {
        return "Список всех жанров :" + System.lineSeparator() + genreRepository.findAll();
    }

}
