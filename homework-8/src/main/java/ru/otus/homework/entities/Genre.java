package ru.otus.homework.entities;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Genre {

    @Id
    private String id;
    private String name;
    private List<Book> books;

    public Genre(String name, Book... books) {
        this.name = name;
        this.books = new LinkedList<>(List.of(books));
    }
}
