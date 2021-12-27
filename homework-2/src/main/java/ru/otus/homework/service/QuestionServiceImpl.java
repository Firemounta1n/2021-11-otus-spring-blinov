package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.QuestionsDao;
import ru.otus.homework.domain.Question;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionsDao dao;

    @Override
    public List<Question> getAllQuestions() {
        return dao.getAll();
    }

}
