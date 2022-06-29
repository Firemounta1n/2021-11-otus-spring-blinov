package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.services.BookService;
import ru.otus.homework.services.ScannerService;

@ShellComponent
@RequiredArgsConstructor
public class BookCommands {

    private final BookService bookService;

    private final ScannerService scannerService;

    @ShellMethod(value = "Add new book", key = {"ab", "add_book"})
    public String addBook() {
        System.out.println("Введите название книги");
        val title = scannerService.getScannerInNext();
        System.out.println("Введите автора книги");
        val authorFio = scannerService.getScannerInNext();
        System.out.println("Введите жанр книги");
        val genreName = scannerService.getScannerInNext();

        val book = bookService.addNewBook(new BookDto()
                .setTitle(title)
                .setAuthorFio(authorFio)
                .setGenreName(genreName)
        );
        return "Книга '" + book.getTitle() + "' добавлена в библиотеку";
    }

    @ShellMethod(value = "Show all books", key = {"sb", "show_books"})
    public String showAllBooks() {
        return "Список всех книг :" + System.lineSeparator() + bookService.getAllBooks();
    }

    @ShellMethod(value = "Get book by title", key = {"gt", "get_by_title"})
    public String getBookByTitle() {
        System.out.println("Введите название книги");
        val bookTitle = scannerService.getScannerInNext();
        return "Книга по названию :" + System.lineSeparator() + bookService.getBookByTitle(bookTitle);
    }

    @ShellMethod(value = "Get books by author", key = {"ga", "get_by_author"})
    public String getBooksByAuthor() {
        System.out.println("Введите автора книги");
        val fio = scannerService.getScannerInNext();
        return "Список книг по автору :" + System.lineSeparator() + bookService.getBooksByFio(fio);
    }

    @ShellMethod(value = "Get books by genre", key = {"gg", "get_by_genre"})
    public String getBooksByGenre() {
        System.out.println("Введите жанр книги");
        val genreName = scannerService.getScannerInNext();
        return "Список книг по жанру :" + System.lineSeparator() + bookService.getBooksByGenreName(genreName);
    }

    @ShellMethod(value = "Update book title by title", key = {"updt", "update_book_title"})
    public String updateBookTitleById() {
        System.out.println("Введите название книги для которой требуется обновление");
        val bookTitle = scannerService.getScannerInNext();
        System.out.println("Введите новое название книги");
        val newBookTitle = scannerService.getScannerInNext();
        return "Книга обновлена :" + System.lineSeparator() + bookService.updateBookTitle(bookTitle, newBookTitle);
    }

    @ShellMethod(value = "Delete book by Title", key = {"delb", "delete_book"})
    public String deleteBookById() {
        System.out.println("Введите название книги для удаления");
        val id = scannerService.getScannerInNext();
        bookService.deleteBookByTitle(id);
        return "Книга c id = " + id + " удалена";
    }

}
