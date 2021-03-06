package ru.otus.homework.entities;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String authorId;
    private String genreId;

    public Book(String title, String authorId, String genreId) {
        this.title = title;
        this.authorId = authorId;
        this.genreId = genreId;
    }
}
