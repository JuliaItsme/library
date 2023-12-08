package ru.goryacheva.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.goryacheva.library.models.Book;
import ru.goryacheva.library.models.Person;
import ru.goryacheva.library.util.BookMapper;
import ru.goryacheva.library.util.PersonMapper;

import java.util.List;
import java.util.Optional;

@Component
public class BooksDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BooksDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Book getById(int id) {
        return jdbcTemplate.query(
                        "SELECT * FROM Book WHERE book_id = ?", new BookMapper(), id)
                .stream().findAny().orElse(null);
    }

    public List<Book> getAll() {
        return jdbcTemplate.query("SELECT * FROM Book", new BookMapper());
    }

    public void save(Book book) {
        jdbcTemplate.update(
                "INSERT INTO Book(title,  author, year_of_production) VALUES (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYearOfProduction());
    }

    public void update(int id, Book bookUp) {
        jdbcTemplate.update("UPDATE Book SET title = ?, author = ?, year_of_production = ? WHERE book_id = ?",
                bookUp.getTitle(), bookUp.getAuthor(), bookUp.getYearOfProduction(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id = ?", id);
    }

    // Join'им таблицы Book и Person и получаем человека, которому принадлежит книга с указанным id
    // Выбираем все колонки таблицы Person из объединенной таблицы
    public Optional<Person> getPersonByBook(int bookId) {
        return jdbcTemplate.query(
                "SELECT person.* FROM book JOIN person ON  book.person_id = person.person_id WHERE book_id = ?",
                new PersonMapper(), bookId).stream().findAny();
    }

    // Назначает книгу человеку (этот метод вызывается, когда человек забирает книгу из библиотеки)
    public void addPersonByBook(int id, Person person) {
        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE book_id = ?", person.getId(), id);
    }

    // Освобождает книгу (этот метод вызывается, когда человек возвращает книгу в библиотеку)
    public void deletePersonByBook(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id = NULL WHERE book_id = ?", id);
    }
}
