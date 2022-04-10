package ru.otus.homework.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.entities.Book;
import ru.otus.homework.entities.Comment;
import ru.otus.homework.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final CommentsService commentsService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public Book addNewBook(Book book) {
        if (book.getComments().size() > 0) {
            List<Comment> comments = new ArrayList<>();
            book.getComments().forEach(c -> {
                Comment comment = commentsService.saveComment(c);
                comments.add(comment);
            });
            book.setComments(comments);
        }
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public Optional<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public Optional<Book> addCommentToBook(String title, Comment comment) {
        Optional<Book> book = bookRepository.findByTitle(title);
        book.ifPresent(b -> {
            commentsService.saveComment(comment);
            b.getComments().add(comment);
            bookRepository.save(b);
        });
        return book;
    }

    @Override
    public Optional<Book> updateBookTitle(String currentTitle, String newTitle) {
        Optional<Book> book = bookRepository.findByTitle(currentTitle);
        book.ifPresent(b -> {
            Optional<Book> checkNewTitleExists = bookRepository.findByTitle(newTitle);
            checkNewTitleExists.ifPresent(newG -> {
                System.out.println("Такая книга уже существует");
                throw new RuntimeException();
            });
            b.setTitle(newTitle);
            bookRepository.save(b);
            authorService.updateAuthorBook(b);
            genreService.updateGenreBook(b);
        });
        return book;
    }

    @Override
    public void deleteCommentFromBook(String title, String commentId) {
        Optional<Book> book = bookRepository.findByTitle(title);
        book.ifPresent(b -> {
            Optional<Comment> comment = commentsService.getCommentById(commentId);
            comment.ifPresent(c -> {
                bookRepository.deleteCommentFromBook(c);
                commentsService.deleteCommentById(commentId);
            });
        });
    }

    @Override
    public void deleteCommentByIdFromAllBooks(String id) {
        commentsService.deleteCommentById(id);
        getAllBooks().forEach(b -> deleteCommentFromBook(b.getTitle(), id));
    }

    @Override
    public void deleteBookByTitle(String title) {
        Optional<Book> book = bookRepository.findByTitle(title);
        book.ifPresent(b -> {
            b.getComments().forEach(c -> commentsService.deleteCommentById(c.getId()));
            authorService.deleteBookFromAuthor(book.get());
            genreService.deleteBookFromGenre(book.get());
            bookRepository.deleteById(b.getId());
        });
    }
}
