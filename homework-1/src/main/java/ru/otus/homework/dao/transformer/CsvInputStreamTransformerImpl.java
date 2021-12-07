package ru.otus.homework.dao.transformer;

import com.opencsv.bean.CsvToBeanBuilder;
import ru.otus.homework.dao.reader.CsvInputStreamReader;
import ru.otus.homework.domain.Question;

import java.util.List;

public class CsvInputStreamTransformerImpl implements CsvInputStreamTransformer {

    private final CsvInputStreamReader reader;

    public CsvInputStreamTransformerImpl(CsvInputStreamReader reader) {
        this.reader = reader;
    }

    @Override
    public List<Question> parseInputStreamToQuestions() {
        return new CsvToBeanBuilder<Question>(reader.getInputStreamReader())
                .withType(Question.class)
                .build()
                .parse();
    }
}
