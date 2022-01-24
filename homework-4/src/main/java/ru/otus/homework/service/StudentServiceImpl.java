package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Student;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private Student student;

    private final ScannerService scannerService;

    private final MessageService messageService;

    @Override
    public void createStudent() {
        System.out.print(messageService.getMessage("student.lastname") + " ");
        String lastName = scannerService.getScannerInNext();
        System.out.print(messageService.getMessage("student.firstname") + " ");
        String firstName = scannerService.getScannerInNext();
        System.out.println(messageService.getMessage("student.greetings",
                new String[] {lastName, firstName}));
        student = new Student(firstName, lastName);
    }

    @Override
    public void cleanStudent() {
        this.student = null;
    }

    @Override
    public Student getStudent() {
        return this.student;
    }
}
