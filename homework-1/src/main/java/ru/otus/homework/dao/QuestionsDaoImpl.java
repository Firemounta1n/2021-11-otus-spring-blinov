package ru.otus.homework.dao;

import ru.otus.homework.dao.transformer.CsvInputStreamTransformer;
import ru.otus.homework.domain.Question;

import java.util.List;

public class QuestionsDaoImpl implements QuestionsDao {


    private final CsvInputStreamTransformer transformer;

    public QuestionsDaoImpl(CsvInputStreamTransformer transformer) {
        this.transformer = transformer;
    }

    @Override
    public List<Question> getAll() {
        return transformer.parseInputStreamToQuestions();
    }

}
