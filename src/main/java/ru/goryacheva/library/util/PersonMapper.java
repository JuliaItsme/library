package ru.goryacheva.library.util;

import org.springframework.jdbc.core.RowMapper;
import ru.goryacheva.library.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(rs.getInt("person_id"));
        person.setFullName(rs.getString("full_name"));
        person.setYearOfBirth(rs.getInt("year_of_birth"));
        return person;
    }
}
