package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final StudentService studentService;

    private final AnswerService answerService;

    private final MessageService messageService;

    @Override
    public boolean makeExam() {
        if (answerService.getCorrectAnswersCount() > 3) {
            System.out.println(messageService.getMessage(
                    "exam.complete.success",
                    new String[]{
                            studentService.getStudent().getLastName(),
                            studentService.getStudent().getFirstName()}));
            return true;
        } else {
            System.out.println(messageService.getMessage(
                    "exam.complete.fail",
                    new String[]{
                            studentService.getStudent().getLastName(),
                            studentService.getStudent().getFirstName()}));
            return false;
        }
    }
}
