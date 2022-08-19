package ru.otus.homework.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import ru.otus.homework.entities.Author;
import ru.otus.homework.entities.Book;
import ru.otus.homework.entities.Comment;
import ru.otus.homework.entities.Genre;

import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    @ChangeSet(order = "000", id = "dropDB", author = "firemounta1n", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "init", author = "firemounta1n", runAlways = true)
    public void init(MongockTemplate template) {
        Author author1 = new Author(ObjectId.get().toString(), "Author1");
        Author author2 = new Author(ObjectId.get().toString(), "Author2");

        Genre genre1 = new Genre(ObjectId.get().toString(), "Genre1");
        Genre genre2 = new Genre(ObjectId.get().toString(), "Genre2");

        Book book1 = template.save(new Book(null, "Book1", author1, genre1));
        Book book2 = template.save(new Book(null, "Book2", author2, genre2));
        template.save(new Book(null, "Book3", author1, genre1));

        template.insertAll(List.of(
                new Comment(null, "Comment1", book1),
                new Comment(null, "Comment2", book2),
                new Comment(null, "Comment3", book1)));
    }
}
