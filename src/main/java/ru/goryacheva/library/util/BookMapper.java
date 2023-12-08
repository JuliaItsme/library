package ru.goryacheva.library.util;

import org.springframework.jdbc.core.RowMapper;
import ru.goryacheva.library.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("book_id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setYearOfProduction(rs.getInt("year_of_production"));
        return book;
    }
}
