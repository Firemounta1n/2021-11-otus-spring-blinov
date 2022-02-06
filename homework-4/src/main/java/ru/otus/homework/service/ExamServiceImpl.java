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
    public String makeExam() {
        String[] studentFullName = new String[]{
                studentService.getStudent().getLastName(),
                studentService.getStudent().getFirstName()};
        if (answerService.getCorrectAnswersCount() > 3) {
            return messageService.getMessage("exam.complete.success", studentFullName);
        } else {
            return messageService.getMessage("exam.complete.fail", studentFullName);
        }
    }
}
