package ru.otus.homework.dao.transformer;

import ru.otus.homework.domain.Question;

import java.util.List;

public interface CsvInputStreamTransformer {

    List<Question> parseInputStreamToQuestions();
}
