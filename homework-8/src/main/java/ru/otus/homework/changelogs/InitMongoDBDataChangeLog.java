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

    private Comment good;
    private Comment awesome;
    private Comment bad;
    private Comment normal;
    private Comment norm;
    private Comment cool;
    private Comment cool2;

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

    @ChangeSet(order = "003", id = "initComments", author = "firemounta1n", runAlways = true)
    public void initComments(CommentsRepository repository) {
        good = repository.save(new Comment("Good!"));
        awesome = repository.save(new Comment("Awesome!"));
        bad = repository.save(new Comment("Bad!"));
        normal = repository.save(new Comment("Normal"));
        norm = repository.save(new Comment("Norm"));
        cool = repository.save(new Comment("Cool!"));
        cool2 = repository.save(new Comment("Cool2!"));
    }

    @ChangeSet(order = "004", id = "initBooks", author = "firemounta1n", runAlways = true)
    public void initBooks(BookRepository repository) {
        repository.save(new Book("Voina i mir", lnTolstoy, epicNovel, good, norm));
        repository.save(new Book("Voina i mir 2", lnTolstoy2, epicNovel2, cool, bad));
        repository.save(new Book("Voina i mir 3", lnTolstoy3, epicNovel3, cool2, normal));
        repository.save(new Book("Voina i mir 4", lnTolstoy4, epicNovel3, awesome));
        repository.save(new Book("Voina i mir 5", lnTolstoy4, epicNovel));
    }
}
