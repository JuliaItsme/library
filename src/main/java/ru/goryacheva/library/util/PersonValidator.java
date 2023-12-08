package ru.goryacheva.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.goryacheva.library.dao.PersonDAO;
import ru.goryacheva.library.models.Person;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors e) {

        Person person = (Person) o;

        if(personDAO.getByFullName(person.getFullName()).isPresent()) {
            e.rejectValue("fullName", "", "This full name is already taken");
        }
    }
}

