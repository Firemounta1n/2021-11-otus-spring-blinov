package ru.otus.homework.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.entities.Author;
import ru.otus.homework.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public Optional<Author> updateAuthorById(String authorId, String authorFio) {
        return authorRepository.findById(authorId)
                .map(author -> {
                    author.setFio(authorFio);
                    return authorRepository.save(author);
                });
    }

    @Override
    @Transactional
    public Optional<Author> updateAuthorByFio(String currentAuthorFio, String newAuthorFio) {
        return authorRepository.findAuthorByFio(currentAuthorFio)
                .map(author -> {
                    author.setFio(newAuthorFio);
                    return authorRepository.save(author);
                });
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
}
