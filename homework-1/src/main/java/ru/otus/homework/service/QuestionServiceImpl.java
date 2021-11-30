package ru.otus.homework.service;

import lombok.extern.slf4j.Slf4j;
import ru.otus.homework.dao.QuestionsDao;
import ru.otus.homework.domain.Question;

import java.util.List;

@Slf4j
public class QuestionServiceImpl implements QuestionService {

    private final QuestionsDao dao;

    public QuestionServiceImpl(QuestionsDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Question> getAllQuestions() {
        return dao.getAll();
    }

    @Override
    public void printAllQuestions() {
        getAllQuestions().forEach(question ->
                log.info("Question №" + question.getId() + ": " + question.getText()));
    }
}
