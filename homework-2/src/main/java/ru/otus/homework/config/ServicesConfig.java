package ru.otus.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework.dao.QuestionsDao;
import ru.otus.homework.service.QuestionService;
import ru.otus.homework.service.QuestionServiceImpl;

@Configuration
public class ServicesConfig {

    @Bean
    public QuestionService questionService(QuestionsDao questionsDao) {
        return new QuestionServiceImpl(questionsDao);
    }
}
