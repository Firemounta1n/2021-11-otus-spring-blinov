package ru.otus.homework.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.homework.domain.Question;

import java.util.List;

class QuestionDaoSimpleTest {

    @Test
    void findAll() {
        QuestionDaoSimple questionDaoSimple = new QuestionDaoSimple("questions.csv");
        List<Question> questionsActual = questionDaoSimple.findAll();
        Assertions.assertEquals(5, questionsActual.size());
    }
}