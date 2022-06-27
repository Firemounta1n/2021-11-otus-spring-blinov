package ru.otus.homework.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import ru.otus.homework.entities.Author;
import ru.otus.homework.entities.Book;
import ru.otus.homework.entities.Genre;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor
public class BookDto {

    private String id;

    private String title;

    private String authorFio;

    private String genreName;

    public Book toDomainObject(String authorId, String genreId) {
        return new Book()
                .setId(this.id)
                .setTitle(this.title)
                .setAuthorId(authorId)
                .setGenreId(genreId);
    }

    public BookDto fromDomainObject(Book book, Author author, Genre genre) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.authorFio = author.getFio();
        this.genreName = genre.getName();
        return this;
    }
}
