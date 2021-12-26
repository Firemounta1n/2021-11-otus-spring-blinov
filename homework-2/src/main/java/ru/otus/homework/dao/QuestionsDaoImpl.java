package ru.otus.homework.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.homework.dao.transformer.CsvInputStreamTransformer;
import ru.otus.homework.domain.Question;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionsDaoImpl implements QuestionsDao {

    private final CsvInputStreamTransformer transformer;

    @Override
    public List<Question> getAll() {
        return transformer.parseInputStreamToQuestions();
    }

}
