package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.repositories.AuthorRepository;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final ScannerService scannerService;

    @Transactional
    @Override
    public String getBooksByAuthorFio() {
        System.out.println("Введите автора книги");
        val fio = scannerService.getScannerInNext();

        return authorRepository.findBookByFio(fio).toString();
    }
}
