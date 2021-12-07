package ru.otus.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private Scanner scanner;

    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl();
        studentService.setScanner(scanner);
    }

    @Test
    void getStudentTest() {
        studentService.createStudent();
        Assertions.assertNotNull(studentService.getStudent());
    }
}