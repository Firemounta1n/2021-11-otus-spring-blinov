package ru.otus.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import ru.otus.homework.entities.Author;
import ru.otus.homework.entities.Book;
import ru.otus.homework.entities.Comment;
import ru.otus.homework.entities.Genre;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;

    private String title;

    private AuthorDto author;

    private GenreDto genre;

    private List<String> comments;

    public Book toDomainObject() {
        return new Book()
                .setId(id)
                .setTitle(title)
                .setAuthor(author.toDomainObject())
                .setGenre(new Genre().setName(genre.getName()))
                .setComments(comments != null
                        ? comments.stream()
                        .map(comment -> new Comment().setText(comment))
                        .collect(Collectors.toList())
                        : Collections.emptyList());
    }

    public static BookDto fromDomainObject(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                new AuthorDto(book.getAuthor().getId(), book.getAuthor().getFio()),
                new GenreDto(book.getGenre().getName()),
                book.getComments().stream()
                        .map(Comment::getText)
                        .collect(Collectors.toList()));
    }
}
