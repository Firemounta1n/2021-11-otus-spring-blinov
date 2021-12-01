package ru.otus.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.homework.dao.QuestionsDao;
import ru.otus.homework.dao.QuestionsDaoImpl;
import ru.otus.homework.dao.loader.CsvResourceLoader;
import ru.otus.homework.dao.loader.CsvResourceLoaderImpl;
import ru.otus.homework.dao.reader.CsvResourceReader;
import ru.otus.homework.dao.reader.CsvResourceReaderImpl;
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
    public CsvResourceReader csvResourceReader(CsvResourceLoader csvResourceLoader) {
        return new CsvResourceReaderImpl(csvResourceLoader);
    }

    @Bean
    public CsvInputStreamTransformer csvInputStreamTransformer(CsvResourceReader csvResourceReader) {
        return new CsvInputStreamTransformerImpl(csvResourceReader);
    }

    @Bean
    public QuestionsDao questionsDao(CsvInputStreamTransformer csvInputStreamTransformer) {
        return new QuestionsDaoImpl(csvInputStreamTransformer);
    }
}
