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

    private final MessageService messageService;

    @Override
    public List<Question> getAllQuestions() {
        return dao.getAll();
    }

    @Override
    public void printQuestion(Question question) {
        System.out.println(messageService.getMessage("question.number") + question.getId() + ": " + question.getText());
        System.out.println(question.getAnswer1());
        System.out.println(question.getAnswer2());
        System.out.println(question.getAnswer3());
        System.out.println(question.getAnswer4());
    }

}
