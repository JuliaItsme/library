package ru.goryacheva.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.goryacheva.library.dao.BooksDAO;
import ru.goryacheva.library.dao.PersonDAO;
import ru.goryacheva.library.models.Book;
import ru.goryacheva.library.models.Person;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksDAO booksDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BooksDAO booksDAO,
                           PersonDAO personDAO) {
        this.booksDAO = booksDAO;
        this.personDAO = personDAO;
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, @ModelAttribute("person") Person p, Model model) {
        model.addAttribute("book", booksDAO.getById(id));
        Optional<Person> person = booksDAO.getPersonByBook(id);
        if (person.isPresent())
            model.addAttribute("person", person.get());
        else
            model.addAttribute("people", personDAO.getAll());
        return "books/show";
    }

    @GetMapping()
    public String showAllBooks(Model model) {
        model.addAttribute("books", booksDAO.getAll());
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult result) {
        if (result.hasErrors())
            return "books/new";

        booksDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksDAO.getById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         @PathVariable("id") int id, BindingResult result) {
        if (result.hasErrors())
            return "books/edit";

        booksDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/choose")
    public String choosePerson(@PathVariable("id") int id, @ModelAttribute("person") Person person) {

        //у person назначено только поле id, остальные null
        booksDAO.addPersonByBook(id, person);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/clean")
    public String cleanPerson(@PathVariable("id") int id) {
        booksDAO.deletePersonByBook(id);
        return "redirect:/books/" + id;
    }
}
