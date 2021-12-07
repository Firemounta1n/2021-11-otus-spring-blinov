package ru.otus.homework.dao.reader;

import ru.otus.homework.dao.loader.CsvResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CsvResourceReaderImpl implements CsvResourceReader {

    private final CsvResourceLoader loader;

    public CsvResourceReaderImpl(CsvResourceLoader loader) {
        this.loader = loader;
    }

    @Override
    public InputStreamReader getInputStreamReader() {
        return new InputStreamReader(getCsvInputStream());
    }

    private InputStream getCsvInputStream() {
        try {
            return loader.getCsvResource().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return InputStream.nullInputStream();
        }
    }
}
