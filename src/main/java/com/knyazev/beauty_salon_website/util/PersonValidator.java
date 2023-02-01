package com.knyazev.beauty_salon_website.util;

import com.knyazev.beauty_salon_website.models.Person;
import com.knyazev.beauty_salon_website.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        personDetailsService.validPeron((Person) target, errors,
                "username", "email", "numberPhone", "age");
    }
}
