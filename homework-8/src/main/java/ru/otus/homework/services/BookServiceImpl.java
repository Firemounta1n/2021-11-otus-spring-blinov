package ru.otus.homework.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.entities.Author;
import ru.otus.homework.entities.Book;
import ru.otus.homework.entities.Comment;
import ru.otus.homework.entities.Genre;
import ru.otus.homework.repositories.AuthorRepository;
import ru.otus.homework.repositories.BookRepository;
import ru.otus.homework.repositories.CommentsRepository;
import ru.otus.homework.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentsRepository commentsRepository;

    @Override
    public Book addNewBook(Book book) {
        if (book.getAuthor() != null) {
            Author author = authorRepository.save(book.getAuthor());
            book.setAuthor(author);
        }
        if (book.getGenre() != null) {
            Genre genre = genreRepository.save(book.getGenre());
            book.setGenre(genre);
        }
        if (book.getComments().size() > 0) {
            List<Comment> comments = new ArrayList<>();
            book.getComments().forEach(comment -> {
                Comment comment1 = commentsRepository.save(comment);
                comments.add(comment1);
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
    public List<Book> getBooksByFio(String fio) {
        return bookRepository.findByAuthorFio(fio);
    }

    @Override
    public List<Book> getBooksByGenreName(String name) {
        return bookRepository.findByGenreName(name);
    }

    @Override
    @Transactional
    public Book addCommentToBook(String title, Comment comment) {
        Optional<Book> book = bookRepository.findByTitle(title);
        if (book.isPresent()) {
            commentsRepository.save(comment);
            book.get().getComments().add(comment);
            bookRepository.save(book.get());
            return book.get();
        } else {
            return new Book();
        }
    }

    @Override
    @Transactional
    public Book updateBookTitle(String currentTitle, String title) {
        Optional<Book> book = bookRepository.findByTitle(currentTitle);
        return book.map(b -> {
            b.setTitle(title);
            return bookRepository.save(b);
        }).orElse(null);
    }

    @Override
    public void deleteCommentFromBook(String title, String commentId) {
        Optional<Book> book = bookRepository.findByTitle(title);
        book.flatMap(b -> b.getComments().stream()
                .filter(c -> c.getId().equals(commentId))
                .findFirst()).ifPresent(foundComment -> commentsRepository.deleteById(foundComment.getId()));

    }

    @Override
    public void deleteBookByTitle(String title) {
        Optional<Book> book = bookRepository.findByTitle(title);
        book.ifPresent(b -> {
            b.getComments().forEach(c -> commentsRepository.deleteById(c.getId()));
            bookRepository.deleteById(b.getId());
        });
    }
}
