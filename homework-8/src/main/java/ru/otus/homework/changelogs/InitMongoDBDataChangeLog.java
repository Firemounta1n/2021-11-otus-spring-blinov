package ru.otus.homework.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.homework.entities.Author;
import ru.otus.homework.entities.Book;
import ru.otus.homework.entities.Comment;
import ru.otus.homework.entities.Genre;
import ru.otus.homework.repositories.AuthorRepository;
import ru.otus.homework.repositories.BookRepository;
import ru.otus.homework.repositories.CommentsRepository;
import ru.otus.homework.repositories.GenreRepository;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Author lnTolstoy;
    private Author lnTolstoy2;
    private Author lnTolstoy3;
    private Author lnTolstoy4;

    private Genre epicNovel;
    private Genre epicNovel2;
    private Genre epicNovel3;

    @ChangeSet(order = "000", id = "dropDB", author = "firemounta1n", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "firemounta1n", runAlways = true)
    public void initAuthors(AuthorRepository repository) {
        lnTolstoy = repository.save(new Author("L.N. Tolstoy"));
        lnTolstoy2 = repository.save(new Author("L.N. Tolstoy 2"));
        lnTolstoy3 = repository.save(new Author("L.N. Tolstoy 3"));
        lnTolstoy4 = repository.save(new Author("L.N. Tolstoy 4"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "firemounta1n", runAlways = true)
    public void initGenres(GenreRepository repository) {
        epicNovel = repository.save(new Genre("Epic novel"));
        epicNovel2 = repository.save(new Genre("Epic novel 2"));
        epicNovel3 = repository.save(new Genre("Epic novel 3"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "firemounta1n", runAlways = true)
    public void initBooks(BookRepository repository) {
        repository.save(new Book("Voina i mir", lnTolstoy.getId(), epicNovel.getId()));
        repository.save(new Book("Voina i mir 2", lnTolstoy2.getId(), epicNovel2.getId()));
        repository.save(new Book("Voina i mir 3", lnTolstoy3.getId(), epicNovel3.getId()));
        repository.save(new Book("Voina i mir 4", lnTolstoy4.getId(), epicNovel3.getId()));
        repository.save(new Book("Voina i mir 5", lnTolstoy4.getId(), epicNovel.getId()));
    }

    @ChangeSet(order = "004", id = "initComments", author = "firemounta1n", runAlways = true)
    public void initComments(BookRepository bookRepository, CommentsRepository repository) {
        bookRepository.findBookByTitle("Voina i mir").ifPresent(book -> {
            repository.save(new Comment(book.getId(),"Good!"));
            repository.save(new Comment(book.getId(), "Norm"));
        });
        bookRepository.findBookByTitle("Voina i mir 2").ifPresent(book -> {
            repository.save(new Comment(book.getId(), "Cool!"));
            repository.save(new Comment(book.getId(), "Bad!"));
        });
        bookRepository.findBookByTitle("Voina i mir 3").ifPresent(book -> {
            repository.save(new Comment(book.getId(),"Cool2!"));
            repository.save(new Comment(book.getId(),"Normal"));
        });
        bookRepository.findBookByTitle("Voina i mir 4")
                .ifPresent(book -> repository.save(new Comment(book.getId(), "Awesome!")));
    }
}
