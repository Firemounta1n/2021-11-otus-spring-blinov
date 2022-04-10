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
public class Book {

    @Id
    private String id;
    private String title;
    private List<Comment> comments;

    public Book(String title, Comment... comments) {
        this.title = title;
        this.comments = new LinkedList<>(List.of(comments));
    }
}
