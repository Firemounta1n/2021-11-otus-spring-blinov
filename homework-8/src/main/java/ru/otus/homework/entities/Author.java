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
public class Author {

    @Id
    private String id;
    private String fio;

    public Author(String fio) {
        this.fio = fio;
    }
}
