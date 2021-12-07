package ru.otus.homework.domain;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Question {

    @CsvBindByName
    private int id;

    @CsvBindByName
    private String text;

    @CsvBindByName
    private String answer1;

    @CsvBindByName
    private String answer2;

    @CsvBindByName
    private String answer3;

    @CsvBindByName
    private String answer4;

    @CsvBindByName
    private Integer correct;

    public void printQuestion() {
        System.out.println("Question №" + this.getId() + ": " + this.getText());
        System.out.println(this.getAnswer1());
        System.out.println(this.getAnswer2());
        System.out.println(this.getAnswer3());
        System.out.println(this.getAnswer4());
    }

}
