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
    public String createStudent() {
        if (student != null) {
            return messageService.getMessage("student.available");
        }
        System.out.print(messageService.getMessage("student.lastname") + " ");
        String lastName = scannerService.getScannerInNext();
        System.out.print(messageService.getMessage("student.firstname") + " ");
        String firstName = scannerService.getScannerInNext();
        student = new Student(firstName, lastName);
        return messageService.getMessage("student.greetings",
                new String[] {lastName, firstName});
    }

    @Override
    public String cleanStudent() {
        this.student = null;
        return messageService.getMessage("student.logout");
    }

    @Override
    public Student getStudent() {
        return this.student;
    }
}
