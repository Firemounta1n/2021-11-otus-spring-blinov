package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.CommentsService;
import ru.otus.homework.service.GenreService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final CommentsService commentsService;


    @ShellMethod(value = "Add new book", key = {"ab", "add_book"})
    public String addBook() {
        return bookService.addNewBook();
    }

    @ShellMethod(value = "Show all books", key = {"sb", "show_books"})
    public String showAllBooks() {
        return bookService.getAllBooks();
    }

    @ShellMethod(value = "Get books by title", key = {"gt", "get_by_title"})
    public String getBooksByTitle() {
        return bookService.getBooksByTitle();
    }

    @ShellMethod(value = "Get books by author", key = {"ga", "get_by_author"})
    public String getBooksByAuthor() {
        return authorService.getBooksByAuthorFio();
    }

    @ShellMethod(value = "Get books by genre", key = {"gg", "get_by_genre"})
    public String getBooksByGenre() {
        return genreService.getBooksByGenreName();
    }

    @ShellMethod(value = "Update book Title by id", key = {"updt", "update_book_title"})
    public String updateBookTitleById() {
        return bookService.updateBookTitle();
    }

    @ShellMethod(value = "Delete book by id", key = {"delb", "delete_book"})
    public String deleteBookById() {
        return bookService.deleteBook();
    }


    @ShellMethod(value = "Add new comment book", key = {"ac", "add_comment"})
    public String addNewCommentToBook() {
        return commentsService.addNewCommentToBook();
    }

    @ShellMethod(value = "Show all comments", key = {"sc", "show_comments"})
    public String showAllComments() {
        return commentsService.getAllComments();
    }

    @ShellMethod(value = "Get comments by id", key = {"gci", "get_comment_by_id"})
    public String getCommentsById() {
        return commentsService.getCommentById();
    }

    @ShellMethod(value = "Update comment text by id", key = {"updc", "update_comment_text"})
    public String updateCommentTextById() {
        return commentsService.updateCommentById();
    }

    @ShellMethod(value = "Delete comment by id", key = {"delc", "delete_comment"})
    public String deleteCommentById() {
        return commentsService.deleteComment();
    }
}
