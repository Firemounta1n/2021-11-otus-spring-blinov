package ru.otus.homework.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
    public Book updateBook(Book editedBook) {
        Optional<Book> foundBook = bookRepository.findById(editedBook.getId());
        return foundBook.map(b -> {
            if (StringUtils.hasText(editedBook.getTitle())) {
                b.setTitle(editedBook.getTitle());
            }
            return setBookData(editedBook, b);
        }).orElse(null);
    }

    private Book setBookData(Book editedBook, Book currentBook) {
        Optional<Author> foundAuthor = authorRepository.findByFio(editedBook.getAuthor().getFio());
        if (foundAuthor.isPresent()) {
            currentBook.setAuthor(foundAuthor.get());
        } else {
            currentBook.getAuthor().setFio(editedBook.getAuthor().getFio());
        }
        Optional<Genre> foundGenre = genreRepository.findByName(editedBook.getGenre().getName());
        if (foundGenre.isPresent()) {
            currentBook.setGenre(foundGenre.get());
        } else {
            currentBook.getGenre().setName(editedBook.getGenre().getName());
        }
        return bookRepository.save(currentBook);
    }

    @Override
    public Book saveBook(Book book) {
        return setBookData(book, book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return Optional.ofNullable(id).flatMap(i -> bookRepository.findById(id));
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public Set<Book> getBooksByFio(String fio) {
        Optional<Author> author = authorRepository.findByFio(fio);
        return author.map(Author::getBooks).orElse(null);
    }

    @Override
    public Set<Book> getBooksByGenreName(String name) {
        Optional<Genre> genre = genreRepository.findByName(name);
        return genre.map(Genre::getBooks).orElse(null);
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
        System.out.println(bookRepository.findById(id));

        bookRepository.findById(id).ifPresent(bookRepository::delete);
    }
}
