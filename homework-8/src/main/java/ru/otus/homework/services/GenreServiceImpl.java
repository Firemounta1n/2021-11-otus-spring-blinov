package ru.otus.homework.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import ru.otus.homework.entities.Book;
import ru.otus.homework.entities.Genre;
import ru.otus.homework.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<Book> getBooksByGenreName(String name) {
        return genreRepository.getGenreBooks(name);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> getGenreById(String id) {
        return genreRepository.findById(id);
    }

    @Override
    public Optional<Genre> getGenreByName(String name) {
        return genreRepository.findByName(name);
    }

    @Override
    public Genre saveNewGenre(Genre genre) {
        Optional<Genre> foundGenre = genreRepository.findByName(genre.getName());
        foundGenre.ifPresent(g -> {
            System.out.println("Такой Жанр уже существует");
            throw new RuntimeException();
        });
        return genreRepository.save(genre);
    }

    @Override
    public Optional<Genre> updateGenre(String currentName, String newName) {
        Optional<Genre> foundGenre = genreRepository.findByName(currentName);
        return foundGenre.map(g -> {
            Optional<Genre> checkNewGenreExists = genreRepository.findById(newName);
            checkNewGenreExists.ifPresent(newG -> {
                System.out.println("Такой Жанр уже существует");
                throw new RuntimeException();
            });
            g.setName(newName);
            return genreRepository.save(g);
        });
    }

    @Override
    public Optional<Genre> addBook(Genre genre, Book book) {
        Optional<Genre> foundGenre = genreRepository.findOne(Example.of(genre));
        return foundGenre.map(g -> {
            g.getBooks().add(book);
            return genreRepository.save(g);
        });
    }

    @Override
    public void deleteGenreById(String id) {
        Optional<Genre> foundGenre = genreRepository.findById(id);
        foundGenre.ifPresent(g -> {
            if (g.getBooks().size() == 0) {
                genreRepository.delete(g);
            }
        });
    }

    @Override
    public boolean deleteGenreByName(String genreName) {
        Optional<Genre> foundGenre = genreRepository.findByName(genreName);
        AtomicBoolean temp = new AtomicBoolean(false);
        foundGenre.ifPresent(g -> {
            if (g.getBooks().size() == 0) {
               genreRepository.delete(g);
               temp.set(true);
            }
        });
        return temp.get();
    }

    @Override
    public void deleteBookFromGenre(Book book) {
        genreRepository.deleteBookFromGenre(book);
    }

    @Override
    public void updateGenreBook(Book book) {
        genreRepository.updateBookInGenre(book);
    }

    @Override
    public Genre findAndAddBookElseCreateNewGenre(String name, Book book) {
        Optional<Genre> foundGenre = getGenreByName(name);
        return foundGenre
                .map(genre -> addBook(genre, book)
                .orElse(new Genre())).orElseGet(() -> saveNewGenre(new Genre(name, book)));
    }
}