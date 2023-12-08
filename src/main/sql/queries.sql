SELECT full_name FROM person JOIN book ON person.person_id = Book.person_id WHERE book_id = 2;

SELECT person.full_name FROM book JOIN person ON book.person_id = person.person_id WHERE book_id = 2;

UPDATE book SET person_id = 2 WHERE book_id = 2;