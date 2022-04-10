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

    private Comment good;
    private Comment awesome;
    private Comment bad;
    private Comment normal;
    private Comment norm;
    private Comment cool;
    private Comment cool2;

    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;
    private Book book5;

    @ChangeSet(order = "000", id = "dropDB", author = "firemounta1n", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initComments", author = "firemounta1n", runAlways = true)
    public void initComments(CommentsRepository repository) {
        good = repository.save(new Comment("Good!"));
        awesome = repository.save(new Comment("Awesome!"));
        bad = repository.save(new Comment("Bad!"));
        normal = repository.save(new Comment("Normal"));
        norm = repository.save(new Comment("Norm"));
        cool = repository.save(new Comment("Cool!"));
        cool2 = repository.save(new Comment("Cool2!"));
    }

    @ChangeSet(order = "002", id = "initBooks", author = "firemounta1n", runAlways = true)
    public void initBooks(BookRepository repository) {
        book1 = repository.save(new Book("Voina i mir", good, norm));
        book2 = repository.save(new Book("Voina i mir 2", cool, bad));
        book3 = repository.save(new Book("Voina i mir 3", cool2, normal));
        book4 = repository.save(new Book("Voina i mir 4", awesome));
        book5 = repository.save(new Book("Voina i mir 5"));
    }

    @ChangeSet(order = "003", id = "initAuthors", author = "firemounta1n", runAlways = true)
    public void initAuthors(AuthorRepository repository) {
        repository.save(new Author("L.N. Tolstoy", book1));
        repository.save(new Author("L.N. Tolstoy 2", book2));
        repository.save(new Author("L.N. Tolstoy 3", book3));
        repository.save(new Author("L.N. Tolstoy 4", book4, book5));
    }

    @ChangeSet(order = "004", id = "initGenres", author = "firemounta1n", runAlways = true)
    public void initGenres(GenreRepository repository) {
        repository.save(new Genre("Epic novel", book1, book5));
        repository.save(new Genre("Epic novel 2", book2));
        repository.save(new Genre("Epic novel 3", book3, book4));
    }
}
