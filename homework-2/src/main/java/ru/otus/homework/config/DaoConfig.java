package ru.otus.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.homework.dao.QuestionsDao;
import ru.otus.homework.dao.QuestionsDaoImpl;
import ru.otus.homework.dao.loader.CsvResourceLoader;
import ru.otus.homework.dao.loader.CsvResourceLoaderImpl;
import ru.otus.homework.dao.reader.CsvInputStreamReader;
import ru.otus.homework.dao.reader.CsvInputStreamReaderImpl;
import ru.otus.homework.dao.transformer.CsvInputStreamTransformer;
import ru.otus.homework.dao.transformer.CsvInputStreamTransformerImpl;

@Configuration
@PropertySource("/application.properties")
public class DaoConfig {

    @Bean
    public CsvResourceLoader csvResourceLoader(@Value("${csv.path}") String path) {
        return new CsvResourceLoaderImpl(path);
    }

    @Bean
    public CsvInputStreamReader csvInputStreamReader(CsvResourceLoader csvResourceLoader) {
        return new CsvInputStreamReaderImpl(csvResourceLoader);
    }

    @Bean
    public CsvInputStreamTransformer csvInputStreamTransformer(CsvInputStreamReader csvInputStreamReader) {
        return new CsvInputStreamTransformerImpl(csvInputStreamReader);
    }

    @Bean
    public QuestionsDao questionsDao(CsvInputStreamTransformer csvInputStreamTransformer) {
        return new QuestionsDaoImpl(csvInputStreamTransformer);
    }
}
