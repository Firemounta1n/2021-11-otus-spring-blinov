package ru.otus.homework.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.otus.homework.dao.transformer.CsvInputStreamTransformer;
import ru.otus.homework.domain.Question;

import java.util.Collections;
import java.util.List;

class QuestionsDaoImplTest {

    @Mock
    private CsvInputStreamTransformer transformer;

    private QuestionsDao questionsDao;

    @BeforeEach
    void setUp() {
        questionsDao = new QuestionsDaoImpl(transformer);
    }

    @Test
    void getResourceTest() {
        Question question = new Question();
        question.setId(1);
        question.setText("Text1");

        Mockito.when(transformer.parseInputStreamToQuestions())
                .thenReturn(Collections.singletonList(question));
        List<Question> questions = questionsDao.getAll();
        Assertions.assertEquals(1, questions.size());
    }
}