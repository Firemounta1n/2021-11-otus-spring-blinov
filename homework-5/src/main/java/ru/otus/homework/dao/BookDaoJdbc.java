package ru.otus.homework.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        Integer count = namedParameterJdbcOperations.getJdbcOperations()
                .queryForObject("SELECT COUNT(*) FROM BOOK", Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public void insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("bookTitle", book.getTitle());
        namedParameterJdbcOperations.update(
                "INSERT INTO BOOK (TITLE) VALUES (:bookTitle);",
                parameters, keyHolder
        );
        if (keyHolder.getKey() == null) {
            throw new RuntimeException();
        }
        namedParameterJdbcOperations.update(
                "INSERT INTO AUTHOR (BOOK_ID, FIO) VALUES (:authorBookId, :authorFio);",
                Map.of(
                        "authorBookId", keyHolder.getKey(),
                        "authorFio", book.getAuthor().getFio()
                )
        );
        namedParameterJdbcOperations.update(
                "INSERT INTO GENRE (BOOK_ID, NAME) VALUES (:genreBookId, :genreName);",
                Map.of(
                        "genreBookId", keyHolder.getKey(),
                        "genreName", book.getGenre().getName()
                )
        );
    }

    @Override
    public Book getByBookId(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "SELECT BOOK.ID, BOOK.TITLE, AUTHOR.ID, AUTHOR.FIO, GENRE.ID, GENRE.NAME " +
                        "FROM BOOK " +
                        "JOIN AUTHOR ON AUTHOR.BOOK_ID = BOOK.ID " +
                        "JOIN GENRE ON GENRE.BOOK_ID = BOOK.ID " +
                        "WHERE BOOK.ID = :id",
                params,
                new BookMapper()
        );
    }

    @Override
    public List<Book> getAllByBookTitle(String title) {
        Map<String, Object> params = Collections.singletonMap("title", title);
        return namedParameterJdbcOperations.query(
                "SELECT BOOK.ID, BOOK.title, AUTHOR.ID, AUTHOR.FIO, GENRE.ID, GENRE.NAME " +
                        "FROM BOOK " +
                        "JOIN AUTHOR ON AUTHOR.BOOK_ID = BOOK.ID " +
                        "JOIN GENRE ON GENRE.BOOK_ID = BOOK.ID " +
                        "WHERE BOOK.TITLE = :title",
                params,
                new BookMapper()
        );
    }

    @Override
    public List<Book> getAllByAuthorFio(String fio) {
        Map<String, Object> params = Collections.singletonMap("fio", fio);
        return namedParameterJdbcOperations.query(
                "SELECT BOOK.ID, BOOK.title, AUTHOR.ID, AUTHOR.FIO, GENRE.ID, GENRE.NAME " +
                        "FROM BOOK " +
                        "JOIN AUTHOR ON AUTHOR.BOOK_ID = BOOK.ID " +
                        "JOIN GENRE ON GENRE.BOOK_ID = BOOK.ID " +
                        "WHERE AUTHOR.FIO = :fio", params,
                new BookMapper()
        );
    }

    @Override
    public List<Book> getAllByGenreName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        return namedParameterJdbcOperations.query(
                "SELECT BOOK.ID, BOOK.title, AUTHOR.ID, AUTHOR.FIO, GENRE.ID, GENRE.NAME " +
                        "FROM BOOK " +
                        "JOIN AUTHOR ON AUTHOR.BOOK_ID = BOOK.ID " +
                        "JOIN GENRE ON GENRE.BOOK_ID = BOOK.ID " +
                        "WHERE GENRE.NAME = :name",
                params,
                new BookMapper()
        );
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query(
                "SELECT BOOK.ID, BOOK.title, AUTHOR.ID, AUTHOR.FIO, GENRE.ID, GENRE.NAME " +
                        "FROM BOOK " +
                        "JOIN AUTHOR ON AUTHOR.BOOK_ID = BOOK.ID " +
                        "JOIN GENRE ON GENRE.BOOK_ID = BOOK.ID",
                new BookMapper());
    }

    @Override
    public void updateByBookId(Book book, long id) {
        Map<String, Object> params = Map.of(
                "bookId", id,
                "newTitle", book.getTitle(),
                "newFio", book.getAuthor().getFio(),
                "newName", book.getGenre().getName()
        );

        namedParameterJdbcOperations.update(
                "UPDATE BOOK SET BOOK.TITLE = :newTitle WHERE BOOK.ID = :bookId; " +
                        "UPDATE AUTHOR SET AUTHOR.FIO = :newFio WHERE AUTHOR.BOOK_ID = :bookId; " +
                        "UPDATE GENRE SET GENRE.NAME = :newName WHERE GENRE.BOOK_ID = :bookId;",
                params
        );
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "DELETE FROM AUTHOR WHERE BOOK_ID = :id;" +
                        "DELETE FROM GENRE WHERE BOOK_ID = :id;" +
                        "DELETE FROM BOOK WHERE ID = :id;",
                params
        );
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long bookId = resultSet.getLong("BOOK.ID");
            String bookTitle = resultSet.getString("BOOK.TITLE");
            long authorId = resultSet.getLong("AUTHOR.ID");
            String authorFio = resultSet.getString("AUTHOR.FIO");
            long genreId = resultSet.getLong("GENRE.ID");
            String genreName = resultSet.getString("GENRE.NAME");
            return new Book(bookId, bookTitle,
                    new Author(authorId, bookId, authorFio),
                    new Genre(genreId, bookId, genreName));
        }
    }
}
