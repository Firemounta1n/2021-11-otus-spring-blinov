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
import ru.otus.homework.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    @Override
    public Book addNewBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public Set<Book> getBooksByFio(String fio) {
        Author author = authorRepository.findByFio(fio);
        return author.getBooks();
    }

    @Override
    public Set<Book> getBooksByGenreName(String name) {
        Genre genre = genreRepository.findByName(name);
        return genre.getBooks();
    }

    @Override
    @Transactional
    public Book addCommentToBook(long id, Comment comment) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            book.get().getComments().add(comment);
            bookRepository.save(book.get());
            return book.get();
        } else {
            return new Book();
        }
    }

    @Override
    @Transactional
    public Book updateBookTitle(long id, String title) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(b -> {
            b.setTitle(title);
            return bookRepository.save(b);
        }).orElse(null);
    }

    @Override
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }
}
