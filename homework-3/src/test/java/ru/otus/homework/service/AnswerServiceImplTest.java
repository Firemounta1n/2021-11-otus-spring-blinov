package ru.otus.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AnswerServiceImplTest {

    private AnswerServiceImpl answerService;

    @Mock
    private QuestionService questionService;

    @Mock
    private ScannerService scannerService;

    @Mock
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        answerService = new AnswerServiceImpl(questionService, scannerService, messageService);
    }

    @Test
    void getCorrectAnswersCountTest() {
        Assertions.assertEquals(0, answerService.getCorrectAnswersCount());
    }
}