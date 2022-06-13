package ru.otus.homework.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.dto.AuthorDto;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.dto.GenreDto;
import ru.otus.homework.entities.Book;
import ru.otus.homework.services.BookService;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String booksPage(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/createBook")
    public String createBookPage(Model model) {
        model.addAttribute("bookDto", new BookDto().setAuthor(new AuthorDto()).setGenre(new GenreDto()));
        return "createBook";
    }

    @PostMapping("/createBook")
    public String createBookAction(@ModelAttribute("bookDto") BookDto bookDto) {
        bookService.saveBook(bookDto.toDomainObject());
        return "redirect:/";
    }

    @GetMapping("/editBook")
    public String editBookPage(@RequestParam("id") int id, Model model) {
        Book book = bookService.getBookById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("book", book);
        return "editBook";
    }

    @PostMapping("/editBook")
    public String updateBookAction(@ModelAttribute("book") BookDto bookDto) {
        bookService.getBookById(bookDto.getId()).ifPresent(b -> bookService.updateBook(bookDto.toDomainObject()));
        return "redirect:/";
    }

    @GetMapping("/deleteBook")
    public String deleteBookAction(@Param("id") int id) {
        bookService.deleteBook(id);
        return "redirect:/";
    }
}
