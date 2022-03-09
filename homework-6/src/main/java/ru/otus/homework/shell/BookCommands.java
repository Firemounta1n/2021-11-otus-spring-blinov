package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.entities.Author;
import ru.otus.homework.entities.Book;
import ru.otus.homework.entities.Comment;
import ru.otus.homework.entities.Genre;
import ru.otus.homework.services.BookService;
import ru.otus.homework.services.ScannerService;

import java.util.List;

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
        val fio = scannerService.getScannerInNext();
        System.out.println("Введите жанр книги");
        val genreName = scannerService.getScannerInNext();
        System.out.println("Введите комментарий к книге");
        val comment = scannerService.getScannerInNext();
        val book = bookService.addNewBook(new Book()
                .setTitle(title)
                .setAuthor(new Author().setFio(fio))
                .setGenre(new Genre().setName(genreName))
                .setComments(List.of(new Comment().setText(comment)))
        );
        return "Книга '" + book.getTitle() + "' добавлена в библиотеку";
    }

    @ShellMethod(value = "Show all books", key = {"sb", "show_books"})
    public String showAllBooks() {
        return "Список всех книг :" + System.lineSeparator() + bookService.getAllBooks();
    }

    @ShellMethod(value = "Get books by title", key = {"gt", "get_by_title"})
    public String getBooksByTitle() {
        System.out.println("Введите название книги");
        val bookTitle = scannerService.getScannerInNext();
        return "Список книг по названию :" + System.lineSeparator() + bookService.getBooksByTitle(bookTitle);
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

    @ShellMethod(value = "Update book Title by id", key = {"updt", "update_book_title"})
    public String updateBookTitleById() {
        System.out.println("Введите id книги для обновления");
        val bookId = Long.parseLong(scannerService.getScannerInNext());
        System.out.println("Введите название книги");
        val bookTitle = scannerService.getScannerInNext();
        return "Книга обновлена :" + System.lineSeparator() + bookService.updateBookTitle(bookId, bookTitle);
    }

    @ShellMethod(value = "Add new comment book", key = {"ac", "add_comment"})
    public String addNewCommentToBook() {
        System.out.println("Введите id книги");
        val id = Long.parseLong(scannerService.getScannerInNext());
        System.out.println("Введите новый комментарий");
        val newComment = scannerService.getScannerInNext();
        return "Новый комментарий добавлен к книге :" + System.lineSeparator() + bookService.addCommentToBook(id, new Comment().setText(newComment));
    }

    @ShellMethod(value = "Delete book by id", key = {"delb", "delete_book"})
    public String deleteBookById() {
        System.out.println("Введите id книги для удаления");
        val id = Long.parseLong(scannerService.getScannerInNext());
        bookService.deleteBook(id);
        return "Книга c id = " + id + " удалена";
    }

}
