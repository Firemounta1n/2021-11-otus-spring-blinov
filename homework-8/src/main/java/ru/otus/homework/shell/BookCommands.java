package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.entities.Book;
import ru.otus.homework.entities.Comment;
import ru.otus.homework.services.AuthorService;
import ru.otus.homework.services.BookService;
import ru.otus.homework.services.GenreService;
import ru.otus.homework.services.ScannerService;

@ShellComponent
@RequiredArgsConstructor
public class BookCommands {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

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

        val book = bookService.addNewBook(
                new Book(title, new Comment(comment)));

        val author = authorService.findAndAddBookElseCreateNewAuthor(fio, book);

        val genre = genreService.findAndAddBookElseCreateNewGenre(genreName, book);

        return "Книга '" + book.getTitle() + "' Автор - " + author.getFio() + " Жанр - " + genre.getName() + " добавлена в библиотеку";
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

    @ShellMethod(value = "Update book Title by Title", key = {"updt", "update_book_title"})
    public String updateBookTitleById() {
        System.out.println("Введите название книги для которой требуется обновление");
        val bookTitle = scannerService.getScannerInNext();
        System.out.println("Введите новое название книги");
        val newBookTitle = scannerService.getScannerInNext();
        return "Книга обновлена :" + System.lineSeparator() + bookService.updateBookTitle(bookTitle, newBookTitle);
    }

    @ShellMethod(value = "Add new comment book", key = {"ac", "add_comment"})
    public String addNewCommentToBook() {
        System.out.println("Введите название книги для которой нужно добавить комментарий");
        val title = scannerService.getScannerInNext();
        System.out.println("Введите новый комментарий");
        val newComment = scannerService.getScannerInNext();
        return "Новый комментарий добавлен к книге :"
                + System.lineSeparator() + bookService.addCommentToBook(title, new Comment().setText(newComment));
    }

    @ShellMethod(value = "Delete book by Title", key = {"delb", "delete_book"})
    public String deleteBookById() {
        System.out.println("Введите название книги для удаления");
        val tittle = scannerService.getScannerInNext();
        bookService.deleteBookByTitle(tittle);
        return "Книга c названием - " + tittle + " удалена";
    }

    @ShellMethod(value = "Delete comment from book", key = {"delcb", "delete_comment_from_book"})
    public String deleteCommentFromBook() {
        System.out.println("Введите название книги для удаления");
        val title = scannerService.getScannerInNext();
        System.out.println("Введите id комментария для удаления");
        val commentId = scannerService.getScannerInNext();
        bookService.deleteCommentFromBook(title, commentId);
        return "Комментарий c id = " + commentId + " удален из книги";
    }

    @ShellMethod(value = "Delete comment by id from all books", key = {"delc", "delete_comment"})
    public String deleteCommentByIdFromAllBooks() {
        System.out.println("Введите id комментария для удаления");
        val id = scannerService.getScannerInNext();
        bookService.deleteCommentByIdFromAllBooks(id);
        return "Комментарий c id = " + id + " удален из всех книг";
    }

}
