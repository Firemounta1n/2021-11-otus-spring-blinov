package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.repositories.AuthorRepositoryJpa;

@ShellComponent
@RequiredArgsConstructor
public class AuthorCommands {

    private final AuthorRepositoryJpa authorRepositoryJpa;

    @ShellMethod(value = "Show all authors", key = {"sa", "show_authors"})
    public String showAllAuthors() {
        return "Список всех авторов :" + System.lineSeparator() + authorRepositoryJpa.findAll();
    }

}
