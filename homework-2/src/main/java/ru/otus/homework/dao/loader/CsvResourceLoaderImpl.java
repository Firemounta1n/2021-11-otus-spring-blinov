package ru.otus.homework.dao.loader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
@PropertySource("/application.properties")
public class CsvResourceLoaderImpl implements CsvResourceLoader {

    private final String path;

    public CsvResourceLoaderImpl(@Value("${csv.path}") String path) {
        this.path = path;
    }

    @Override
    public Resource getCsvResource() {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        return resourceLoader.getResource(path);
    }

}
