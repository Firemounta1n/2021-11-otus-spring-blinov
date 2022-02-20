package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.repositories.GenreRepository;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final ScannerService scannerService;

    @Transactional
    @Override
    public String getBooksByGenreName() {
        System.out.println("Введите жанр книги");
        val genreName = scannerService.getScannerInNext();

        return genreRepository.findBookByGenreName(genreName).toString();
    }

}
