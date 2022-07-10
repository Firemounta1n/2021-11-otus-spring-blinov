package ru.otus.homework.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.repositories.AuthorRepository;
import ru.otus.homework.rest.dto.AuthorDto;
import ru.otus.homework.utils.AuthorMapper;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AuthorController {

    private final AuthorRepository authorRepository;

    @GetMapping("/authors")
    public List<AuthorDto> readAll() {
        return authorRepository.findAll().stream()
                .map(AuthorMapper::fromDomainObject)
                .collect(Collectors.toList());
    }

}
