package ru.otus.homework.dao.loader;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class CsvResourceLoaderImpl implements CsvResourceLoader {

    private final String path;

    public CsvResourceLoaderImpl(String path) {
        this.path = path;
    }

    @Override
    public Resource getCsvResource() {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        return resourceLoader.getResource(path);
    }

}
