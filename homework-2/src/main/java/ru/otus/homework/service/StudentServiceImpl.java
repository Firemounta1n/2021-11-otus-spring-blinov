package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Student;

import java.util.Scanner;

@Service
public class StudentServiceImpl implements StudentService {

    private Student student;

    private Scanner scanner = new Scanner(System.in);

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void createStudent() {
        System.out.print("Enter your lastname: ");
        String lastName = scanner.next();
        System.out.print("Enter your firstname: ");
        String firstName = scanner.next();
        System.out.println("Hello " + lastName + " " + firstName + ", answer the questions");
        student = new Student(firstName, lastName);
    }

    @Override
    public Student getStudent() {
        return this.student;
    }
}
