package com.knyazev.beauty_salon_website.services;


import com.knyazev.beauty_salon_website.models.Person;
import com.knyazev.beauty_salon_website.repositories.PersonRepository;
import com.knyazev.beauty_salon_website.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;


    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByUsername(s);

        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found!");

        return new PersonDetails(person.get());
    }

    public boolean checkUser(String s) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByUsername(s);
        return person.isEmpty();
    }

    public boolean checkUserEmail(String s) {
        Optional<Person> person = personRepository.findByEmail(s);
        return person.isEmpty();
    }

    public boolean checkNumberPhone(String s) {
        Optional<Person> person = personRepository.findByNumberPhone(s);
        return person.isEmpty();
    }

    public Errors validPeron(Person person, Errors errors,
                             String username, String email, String numberPhone, String age
                             ) {

        if (!checkUser(person.getUsername()))
            errors.rejectValue(username, "", "Человек с таким именем уже существует!");
        if (!checkUserEmail(person.getEmail()))
            errors.rejectValue(email, "", "Человек стакой почтой уже существует!");
        if (!checkNumberPhone(person.getNumberPhone()))
            errors.rejectValue(numberPhone, "", "Человек стаким телефоном уже существует!");
        if(person.getAge()<18)
            errors.rejectValue(age, "","Вам должно быть больше 18!");
        return errors;
    }

    public PersonDetails getCurrentPerson() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (PersonDetails) authentication.getPrincipal();
        } catch (ClassCastException e) {
            return null;
        }
    }
}
