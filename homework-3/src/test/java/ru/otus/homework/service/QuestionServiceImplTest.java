package ru.otus.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.dao.QuestionsDao;
import ru.otus.homework.domain.Question;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private QuestionsDao dao;

    private QuestionServiceImpl questionService;

    @BeforeEach
    void setUp() {
        questionService = new QuestionServiceImpl(dao);
    }

    @Test
    void getAllQuestionsTest() {
        Question question = new Question();
        question.setId(1);
        question.setText("Text1");

        Mockito.when(dao.getAll())
                .thenReturn(Collections.singletonList(question));
        List<Question> questionsActual = questionService.getAllQuestions();
        Assertions.assertEquals(1, questionsActual.size());
    }
}