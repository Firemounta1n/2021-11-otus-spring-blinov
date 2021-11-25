package ru.otus.homework.domain;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Question {

    @CsvBindByName
    private int id;

    @CsvBindByName
    private String text;
}
