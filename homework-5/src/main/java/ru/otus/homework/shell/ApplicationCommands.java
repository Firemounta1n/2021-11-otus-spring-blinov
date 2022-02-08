package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.service.BookService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {

    private final BookService bookService;

    @ShellMethod(value = "Add new book", key = {"a", "add_book"})
    public String addBook() {
        return bookService.addNewBook();
    }

    @ShellMethod(value = "Show all books", key = {"s", "show"})
    public String showAllBooks() {
        return bookService.getAllBooks();
    }

    @ShellMethod(value = "Get books by title", key = {"gt", "get_by_title"})
    public String getBooksByTitle() {
        return bookService.getBooksByTitle();
    }

    @ShellMethod(value = "Get books by author", key = {"ga", "get_by_author"})
    public String getBooksByAuthor() {
        return bookService.getBooksByAuthorFio();
    }

    @ShellMethod(value = "Get books by genre", key = {"gg", "get_by_genre"})
    public String getBooksByGenre() {
        return bookService.getBooksByGenreName();
    }

    @ShellMethod(value = "Update book by id", key = {"upd", "update"})
    public String updateBookById() {
        return bookService.updateBook();
    }

    @ShellMethod(value = "Delete book by id", key = {"del", "delete"})
    public String deleteBookById() {
        return bookService.deleteBook();
    }
}
