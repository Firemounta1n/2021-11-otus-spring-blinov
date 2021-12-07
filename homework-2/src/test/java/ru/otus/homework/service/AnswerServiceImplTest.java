package ru.otus.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

@ExtendWith(MockitoExtension.class)
class AnswerServiceImplTest {

    @Mock
    private Scanner scanner;

    @Mock
    private QuestionService questionService;

    private AnswerServiceImpl answerService;

    @BeforeEach
    void setUp() {
        answerService = new AnswerServiceImpl(questionService);
        answerService.setScanner(scanner);
    }

    @Test
    void getCorrectAnswersCountTest() {
        Assertions.assertEquals(0, answerService.getCorrectAnswersCount());
    }
}