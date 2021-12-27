package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Student;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private Student student;

    private final ScannerService scannerService;

    @Override
    public void createStudent() {
        System.out.print("Enter your lastname: ");
        String lastName = scannerService.getScannerInNext();
        System.out.print("Enter your firstname: ");
        String firstName = scannerService.getScannerInNext();
        System.out.println("Hello " + lastName + " " + firstName + ", answer the questions");
        student = new Student(firstName, lastName);
    }

    @Override
    public Student getStudent() {
        return this.student;
    }
}
