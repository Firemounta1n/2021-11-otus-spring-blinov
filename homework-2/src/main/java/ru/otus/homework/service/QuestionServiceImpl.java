package ru.otus.homework.service;

import ru.otus.homework.dao.QuestionsDao;
import ru.otus.homework.domain.Question;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionsDao dao;

    public QuestionServiceImpl(QuestionsDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Question> getAllQuestions() {
        return dao.getAll();
    }

}
