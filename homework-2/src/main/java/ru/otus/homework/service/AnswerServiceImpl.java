package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Question;

import java.util.Objects;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final QuestionService questionService;

    private final ScannerService scannerService;

    @Override
    public Integer getCorrectAnswersCount() {
        int correctCounter = 0;
        for (Question question : questionService.getAllQuestions()) {
            question.printQuestion();
            System.out.println("Your answer number is?");
            if (Objects.equals(question.getCorrect(), getAnswer(scannerService.getScannerIn()))) {
                correctCounter++;
            }
        }
        return correctCounter;
    }

    private Integer getAnswer(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.next());
        } catch (NumberFormatException e) {
            System.out.println("Please input only numbers");
            return getAnswer(scanner);
        }
    }
}
