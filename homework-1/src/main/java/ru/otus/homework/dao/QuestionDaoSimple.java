package ru.otus.homework.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import ru.otus.homework.domain.Question;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class QuestionDaoSimple implements QuestionDao {

    private final String path;

    public QuestionDaoSimple(String path) {
        this.path = path;
    }

    public List<Question> findAll() {
        return parseCsv(getResource());
    }

    private Resource getResource() {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        return resourceLoader.getResource(path);
    }

    private List<Question> parseCsv(Resource resource) {
        try {
            InputStreamReader streamReader = new InputStreamReader(resource.getInputStream());
            return new CsvToBeanBuilder<Question>(streamReader)
                    .withType(Question.class)
                    .build()
                    .parse();
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

}
