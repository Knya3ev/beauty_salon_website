package com.knyazev.beauty_salon_website.services;

import com.knyazev.beauty_salon_website.models.Person;
import com.knyazev.beauty_salon_website.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public void register(Person person, String role){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole(role);
        personRepository.save(person);
    }
}
