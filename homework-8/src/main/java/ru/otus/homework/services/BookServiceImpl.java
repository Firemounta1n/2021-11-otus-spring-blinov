package ru.otus.homework.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.entities.Author;
import ru.otus.homework.entities.Book;
import ru.otus.homework.entities.Genre;
import ru.otus.homework.repositories.AuthorRepository;
import ru.otus.homework.repositories.BookRepository;
import ru.otus.homework.repositories.CommentsRepository;
import ru.otus.homework.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentsRepository commentsRepository;

    @Override
    public Book addNewBook(BookDto bookDto) {
        Book book = new Book();
        authorRepository.findAuthorByFio(bookDto.getAuthorFio())
                .ifPresentOrElse(
                        a -> book.setAuthorId(a.getId()),
                        () -> {
                            Author author = authorRepository.save(new Author(bookDto.getAuthorFio()));
                            book.setAuthorId(author.getId());
                        });
        genreRepository.findGenreByName(bookDto.getGenreName())
                .ifPresentOrElse(
                        g -> book.setGenreId(g.getId()),
                        () -> {
                            Genre genre = genreRepository.save(new Genre(bookDto.getGenreName()));
                            book.setGenreId(genre.getId());
                        });
        book.setTitle(bookDto.getTitle());
        return bookRepository.save(book);
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(book -> {
                    Author author = authorRepository.findById(book.getAuthorId()).orElseThrow(RuntimeException::new);
                    Genre genre = genreRepository.findById(book.getGenreId()).orElseThrow(RuntimeException::new);
                    return new BookDto().fromDomainObject(book, author, genre);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookDto> getBookByTitle(String title) {
        return bookRepository.findBookByTitle(title).map(book -> {
            Author author = authorRepository.findById(book.getAuthorId()).orElseThrow(RuntimeException::new);
            Genre genre = genreRepository.findById(book.getGenreId()).orElseThrow(RuntimeException::new);
            return new BookDto().fromDomainObject(book, author, genre);
        });
    }

    @Override
    public List<BookDto> getBooksByFio(String fio) {
        return authorRepository.findAuthorByFio(fio)
                .map(author -> bookRepository.findBooksByAuthorId(author.getId()).stream()
                        .map(book -> {
                            Genre genre = genreRepository.findById(book.getGenreId()).orElseThrow(RuntimeException::new);
                            return new BookDto().fromDomainObject(book, author, genre);
                        })
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }

    @Override
    public List<BookDto> getBooksByGenreName(String name) {
        return genreRepository.findGenreByName(name)
                .map(genre -> bookRepository.findBooksByGenreId(genre.getId()).stream()
                        .map(book -> {
                            Author author = authorRepository.findById(book.getAuthorId()).orElseThrow(RuntimeException::new);
                            return new BookDto().fromDomainObject(book, author, genre);
                        })
                        .collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }

    @Override
    @Transactional
    public Book updateBookTitle(String currentTitle, String title) {
        Optional<Book> book = bookRepository.findBookByTitle(currentTitle);
        return book.map(b -> {
            b.setTitle(title);
            return bookRepository.save(b);
        }).orElse(null);
    }

    @Override
    public void deleteBookByTitle(String title) {
        Optional<Book> book = bookRepository.findBookByTitle(title);
        book.ifPresent(b -> commentsRepository.findByBookId(b.getId())
                .forEach(c -> commentsRepository.deleteById(c.getId())));
    }
}
