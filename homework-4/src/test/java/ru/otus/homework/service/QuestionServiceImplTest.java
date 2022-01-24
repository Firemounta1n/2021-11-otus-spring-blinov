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

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    private QuestionService questionService;

    @Mock
    private QuestionsDao dao;

    @Mock
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        questionService = new QuestionServiceImpl(dao, messageService);
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

    @Test
    void printQuestionTest() {
        Question question = new Question();
        question.setId(1);
        question.setAnswer1("1. Text");
        question.setAnswer2("2. Text");
        question.setAnswer3("3. Text");
        question.setAnswer4("4. Text");
        question.setText("Text?");

        Mockito.when(dao.getAll())
                .thenReturn(Collections.singletonList(question));
        Mockito.when(messageService.getMessage(any()))
                .thenReturn("Question №");
        List<Question> questionsActual = questionService.getAllQuestions();
        questionsActual.forEach(question1 -> questionService.printQuestion(question1));

        Assertions.assertEquals(1, questionsActual.size());
    }
}