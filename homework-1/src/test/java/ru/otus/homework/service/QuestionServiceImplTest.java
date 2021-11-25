package ru.otus.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.homework.dao.QuestionDaoSimple;
import ru.otus.homework.domain.Question;

import java.util.List;

class QuestionServiceImplTest {

    @Test
    void getAllQuestions() {
        QuestionDaoSimple questionDaoSimple = new QuestionDaoSimple("questions.csv");
        QuestionServiceImpl questionService = new QuestionServiceImpl(questionDaoSimple);
        List<Question> questionsActual = questionService.getAllQuestions();
        Assertions.assertEquals(5, questionsActual.size());
    }
}