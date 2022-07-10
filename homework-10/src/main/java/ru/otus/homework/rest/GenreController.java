package ru.otus.homework.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.repositories.GenreRepository;
import ru.otus.homework.rest.dto.GenreDto;
import ru.otus.homework.utils.GenreMapper;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class GenreController {

    private final GenreRepository genreRepository;

    @GetMapping("/genres")
    public List<GenreDto> readAll() {
        return genreRepository.findAll().stream()
                .map(GenreMapper::fromDomainObject)
                .collect(Collectors.toList());
    }

}
