package ru.otus.homework.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.entities.Author;
import ru.otus.homework.entities.Book;
import ru.otus.homework.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public List<Book> getBooksByFio(String fio) {
        return authorRepository.getAuthorBooks(fio);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> getAuthorById(String id) {
        return authorRepository.findById(id);
    }

    @Override
    public Optional<Author> getAuthorByFio(String fio) {
        return authorRepository.findByFio(fio);
    }

    @Override
    public Author saveNewAuthor(Author author) {
        Optional<Author> foundAuthor = authorRepository.findByFio(author.getFio());
        foundAuthor.ifPresent(a -> {
            System.out.println("Такой автор уже существует");
            throw new RuntimeException();
        });
        return authorRepository.save(author);
    }

    @Override
    public Optional<Author> updateAuthor(String currentFio, String newFio) {
        Optional<Author> foundAuthor = authorRepository.findByFio(currentFio);
        return foundAuthor.map(a -> {
            Optional<Author> checkNewAuthorExists = authorRepository.findByFio(newFio);
            checkNewAuthorExists.ifPresent(newA -> {
                System.out.println("Такой автор уже существует");
                throw new RuntimeException();
            });
            a.setFio(newFio);
            return authorRepository.save(a);
        });
    }

    @Override
    public Optional<Author> addBook(Author author, Book book) {
        Optional<Author> foundAuthor = authorRepository.findByFio(author.getFio());
        return foundAuthor.map(a -> {
            a.getBooks().add(book);
            return authorRepository.save(a);
        });
    }

    @Override
    public void deleteAuthorById(String id) {
        Optional<Author> foundAuthor = authorRepository.findById(id);
        foundAuthor.ifPresent(g -> {
            if (g.getBooks().size() == 0) {
                authorRepository.delete(g);
            }
        });
    }

    @Override
    public boolean deleteAuthorByFio(String fio) {
        Optional<Author> foundAuthor = authorRepository.findByFio(fio);
        AtomicBoolean temp = new AtomicBoolean(false);
        foundAuthor.ifPresent(a -> {
            if (a.getBooks().size() == 0) {
                authorRepository.delete(a);
                temp.set(true);
            }
        });
        return temp.get();
    }

    @Override
    public void deleteBookFromAuthor(Book book) {
        authorRepository.deleteBookFromAuthor(book);
    }

    @Override
    public void updateAuthorBook(Book book) {
        authorRepository.updateBookInAuthor(book);
    }

    @Override
    public Author findAndAddBookElseCreateNewAuthor(String fio, Book book) {
        Optional<Author> foundAuthor = getAuthorByFio(fio);
        return foundAuthor
                .map(author -> addBook(author, book).orElse(new Author()))
                .orElseGet(() -> saveNewAuthor(new Author(fio, book)));
    }
}
