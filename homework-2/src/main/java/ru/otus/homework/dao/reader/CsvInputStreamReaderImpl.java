package ru.otus.homework.dao.reader;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.homework.dao.loader.CsvResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
@RequiredArgsConstructor
public class CsvInputStreamReaderImpl implements CsvInputStreamReader {

    private final CsvResourceLoader loader;

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
