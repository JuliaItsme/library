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
public class PersonDAO{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Person getById(int id) {
        return jdbcTemplate.query(
                        "SELECT * FROM Person WHERE person_id = ?", new PersonMapper(), id)
                .stream().findAny().orElse(null);
    }

    public List<Person> getAll() {
        return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());
    }

    public void save(Person person) {
        jdbcTemplate.update(
                "INSERT INTO Person(full_name, year_of_birth) VALUES (?, ?)",
                person.getFullName(), person.getYearOfBirth());
    }

    public void update(int id, Person updatePerson) {
        jdbcTemplate.update("UPDATE Person SET full_name = ?, year_of_birth = ? WHERE person_id = ?",
                updatePerson.getFullName(), updatePerson.getYearOfBirth(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id = ?", id);
    }

    // Для валидации уникальности ФИО
    public Optional<Person> getByFullName(String fullName) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE full_name = ?", new PersonMapper(), fullName)
                .stream().findAny();
    }

    // Здесь Join не нужен, уже получили человека с помощью отдельного метода
    public List<Book> getBookByPerson(int personId) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id = ?", new BookMapper(), personId);
    }
}
