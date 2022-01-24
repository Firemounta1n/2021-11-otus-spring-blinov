package ru.otus.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    private StudentService studentService;

    @Mock
    private ScannerService scannerService;

    @Mock
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl(scannerService, messageService);
    }

    @Test
    void getStudentTest() {
        Mockito.when(scannerService.getScannerInNext()).thenReturn("text");

        studentService.createStudent();

        Assertions.assertNotNull(studentService.getStudent());
    }
}