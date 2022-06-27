package ru.otus.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.dto.AuthorDto;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.dto.GenreDto;
import ru.otus.homework.entities.Author;
import ru.otus.homework.entities.Book;
import ru.otus.homework.repositories.AuthorRepository;
import ru.otus.homework.services.BookService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorRepository authorRepository;

    //get view
    @GetMapping("/")
    public String booksView(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books";
    }

    //get view
    @GetMapping("/createOrUpdateBook")
    public String createOrUpdateBookView(@RequestParam("id") @Nullable Long id, Model model) {
        BookDto bookDto = new BookDto();
        if (id != null) {
            Book book = bookService.getBookById(id).orElseThrow(NotFoundException::new);
            bookDto = BookDto.fromDomainObject(book);
        } else {
            bookDto.setAuthor(new AuthorDto()).setGenre(new GenreDto());
        }
        List<Author> authors = authorRepository.findAll();
        List<AuthorDto> authorDtos = authors.stream().map(AuthorDto::fromDomainObject).collect(Collectors.toList());
        model.addAttribute("bookDto", bookDto);
        model.addAttribute("authorDtos", authorDtos);
        return "createOrUpdateBook";
    }

    @PostMapping("/createOrUpdateBook")
    public String createOrUpdateBookAction(@ModelAttribute("bookDto") BookDto bookDto) {
        bookService.getBookById(bookDto.getId()).ifPresentOrElse(
                b -> bookService.updateBook(bookDto.toDomainObject()),
                () -> bookService.saveBook(bookDto.toDomainObject()));
        return "redirect:/";
    }

    @GetMapping("/deleteBook")
    public String deleteBookAction(@Param("id") int id) {
        bookService.deleteBook(id);
        return "redirect:/";
    }
}
