package ru.otus.homework.dao.loader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CsvResourceLoaderImpl implements CsvResourceLoader {

    private final String pathEn;
    private final String pathRu;

    public CsvResourceLoaderImpl(@Value("${csv.path.en}") String pathEn,
                                 @Value("${csv.path.ru}") String pathRu) {
        this.pathEn = pathEn;
        this.pathRu = pathRu;
    }

    @Override
    public Resource getCsvResource() {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        if (Locale.getDefault().equals(Locale.forLanguageTag("ru-RU"))) {
            return resourceLoader.getResource(pathRu);
        } else {
            return resourceLoader.getResource(pathEn);
        }
    }

}
