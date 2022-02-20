package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Comment;
import ru.otus.homework.models.Genre;
import ru.otus.homework.repositories.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final ScannerService scannerService;

    @Transactional
    @Override
    public String addNewBook() {
        System.out.println("Введите название книги");
        val title = scannerService.getScannerInNext();

        System.out.println("Введите автора книги");
        val fio = scannerService.getScannerInNext();

        System.out.println("Введите жанр книги");
        val genreName = scannerService.getScannerInNext();

        System.out.println("Введите комментарий к книге");
        val comment = scannerService.getScannerInNext();

        val book = bookRepository.save(new Book()
                .setTitle(title)
                .setAuthor(new Author().setFio(fio))
                .setGenre(new Genre().setName(genreName))
                .setComments(List.of(new Comment().setText(comment)))
        );

        return "Книга '" + book.getTitle() + "' добавлена в библиотеку";
    }

    @Transactional
    @Override
    public String getAllBooks() {
        return bookRepository.findAll().toString();
    }

    @Transactional
    @Override
    public String getBooksByTitle() {
        System.out.println("Введите название книги");
        val bookTitle = scannerService.getScannerInNext();

        return bookRepository.findByTitle(bookTitle).toString();
    }

    @Transactional
    @Override
    public String updateBookTitle() {
        System.out.println("Введите id книги для обновления");
        long bookId = Long.parseLong(scannerService.getScannerInNext());

        System.out.println("Введите название книги");
        val bookTitle = scannerService.getScannerInNext();

        bookRepository.updateTitleById(bookId, bookTitle);

        return "Книга c id = " + bookId + " обновлена";
    }

    @Transactional
    @Override
    public String deleteBook() {
        System.out.println("Введите id книги для удаления");
        val bookId = Long.parseLong(scannerService.getScannerInNext());

        bookRepository.deleteById(bookId);

        return "Книга c id = " + bookId + " удалена";
    }
}
