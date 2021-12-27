package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final StudentService studentService;

    private final AnswerService answerService;

    @Override
    public boolean makeExam() {
        studentService.createStudent();
        if (answerService.getCorrectAnswersCount() > 3) {
            System.out.println("Congratulations "
                    + studentService.getStudent().getLastName() + " "
                    + studentService.getStudent().getFirstName() + ", you passed the test.");
            return true;
        } else {
            System.out.println("Sorry "
                    + studentService.getStudent().getLastName() + " "
                    + studentService.getStudent().getFirstName() + ", you didn't pass the test.");
            return false;
        }
    }
}
