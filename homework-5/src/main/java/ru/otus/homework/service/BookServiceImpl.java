package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    private final ScannerService scannerService;

    public String addNewBook() {
        System.out.println("Введите название книги");
        String bookTitle = scannerService.getScannerInNext();

        System.out.println("Введите автора книги");
        String bookAuthor = scannerService.getScannerInNext();

        System.out.println("Введите жанр книги");
        String bookGenre = scannerService.getScannerInNext();

        bookDao.insert(new Book(bookTitle,
                new Author(bookAuthor),
                new Genre(bookGenre)));

        return "Книга '" + bookTitle + "' добавлена в библиотеку";
    }

    public String getAllBooks() {
        return bookDao.getAll().toString();
    }

    public String getBooksByTitle() {
        System.out.println("Введите название книги");
        String bookTitle = scannerService.getScannerInNext();

        return bookDao.getAllByBookTitle(bookTitle).toString();
    }

    public String getBooksByAuthorFio() {
        System.out.println("Введите автора книги");
        String bookAuthor = scannerService.getScannerInNext();

        return bookDao.getAllByAuthorFio(bookAuthor).toString();
    }

    public String getBooksByGenreName() {
        System.out.println("Введите жанр книги");
        String bookGenre = scannerService.getScannerInNext();

        return bookDao.getAllByGenreName(bookGenre).toString();
    }

    public String updateBook() {
        System.out.println("Введите id книги для обновления");
        long bookId = Long.parseLong(scannerService.getScannerInNext());

        System.out.println("Введите название книги");
        String bookTitle = scannerService.getScannerInNext();

        System.out.println("Введите автора книги");
        String bookAuthor = scannerService.getScannerInNext();

        System.out.println("Введите жанр книги");
        String bookGenre = scannerService.getScannerInNext();

        bookDao.updateByBookId(new Book(bookTitle,
                new Author(bookAuthor),
                new Genre(bookGenre)), bookId);

        return "Книга c id = " + bookId + " обновлена";
    }

    public String deleteBook() {
        System.out.println("Введите id книги");
        long bookId = Long.parseLong(scannerService.getScannerInNext());

        bookDao.deleteById(bookId);

        return "Книга c id = " + bookId + " удалена";
    }
}
