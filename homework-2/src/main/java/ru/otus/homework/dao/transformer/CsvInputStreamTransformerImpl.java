package ru.otus.homework.dao.transformer;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.homework.dao.reader.CsvInputStreamReader;
import ru.otus.homework.domain.Question;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvInputStreamTransformerImpl implements CsvInputStreamTransformer {

    private final CsvInputStreamReader reader;

    @Override
    public List<Question> parseInputStreamToQuestions() {
        return new CsvToBeanBuilder<Question>(reader.getInputStreamReader())
                .withType(Question.class)
                .build()
                .parse();
    }
}
