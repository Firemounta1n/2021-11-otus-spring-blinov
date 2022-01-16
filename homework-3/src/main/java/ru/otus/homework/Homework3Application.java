package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.homework.service.ExamService;

@SpringBootApplication
public class Homework3Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Homework3Application.class, args);

        ExamService examService = ctx.getBean(ExamService.class);
        examService.makeExam();
    }

}