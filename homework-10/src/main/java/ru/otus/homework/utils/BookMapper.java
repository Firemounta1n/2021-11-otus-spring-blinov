package ru.otus.homework.utils;

import lombok.experimental.UtilityClass;
import ru.otus.homework.entities.Book;
import ru.otus.homework.entities.Comment;
import ru.otus.homework.rest.dto.AuthorDto;
import ru.otus.homework.rest.dto.BookDto;
import ru.otus.homework.rest.dto.GenreDto;

import java.util.Collections;
import java.util.stream.Collectors;

@UtilityClass
public class BookMapper {

    public static Book toDomainObject(BookDto bookDto) {
        return new Book()
                .setId(bookDto.getId())
                .setTitle(bookDto.getTitle())
                .setAuthor(AuthorMapper.toDomainObject(bookDto.getAuthor()))
                .setGenre(GenreMapper.toDomainObject(bookDto.getGenre()))
                .setComments(bookDto.getComments() != null ? bookDto.getComments().stream()
                        .map(comment -> new Comment().setText(comment))
                        .collect(Collectors.toList())
                        : Collections.emptyList());
    }

    public static BookDto fromDomainObject(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                new AuthorDto(book.getAuthor().getId(), book.getAuthor().getFio()),
                new GenreDto(book.getGenre().getId(), book.getGenre().getName()),
                book.getComments().stream()
                        .map(Comment::getText)
                        .collect(Collectors.toList()));
    }
}
