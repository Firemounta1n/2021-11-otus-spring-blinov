package ru.otus.homework.service;

import ru.otus.homework.domain.Student;

public interface StudentService {

    void createStudent();

    void cleanStudent();

    Student getStudent();
}
