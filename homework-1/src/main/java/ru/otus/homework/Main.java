package ru.otus.homework;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.homework.domain.Question;
import ru.otus.homework.service.QuestionService;

import java.util.List;

@Slf4j
public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        QuestionService questionService = context.getBean(QuestionService.class);

        questionService.printAllQuestions();

        context.close();
    }
}
