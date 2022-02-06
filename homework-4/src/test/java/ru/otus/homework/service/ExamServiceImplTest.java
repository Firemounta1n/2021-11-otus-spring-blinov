package ru.otus.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.domain.Student;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ExamServiceImplTest {

    private ExamService examService;

    @Mock
    private AnswerService answerService;

    @Mock
    private StudentService studentService;

    @Mock
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        examService = new ExamServiceImpl(studentService, answerService, messageService);

        Student student = Student.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .build();

        Mockito.when(studentService.getStudent()).thenReturn(student);
    }

    @Test
    void makeExamSuccessTest() {
        Mockito.when(messageService.getMessage(any(), any())).thenReturn("Congratulations {0} {1}, You passed the test!");
        Mockito.when(answerService.getCorrectAnswersCount()).thenReturn(4);
        Assertions.assertEquals("Congratulations {0} {1}, You passed the test!", examService.makeExam());
    }

    @Test
    void makeExamFailedTest() {
        Mockito.when(messageService.getMessage(any(), any())).thenReturn("Sorry {0} {1}, You didn't pass the test.");
        Mockito.when(answerService.getCorrectAnswersCount()).thenReturn(1);
        Assertions.assertEquals("Sorry {0} {1}, You didn't pass the test.", examService.makeExam());
    }
}