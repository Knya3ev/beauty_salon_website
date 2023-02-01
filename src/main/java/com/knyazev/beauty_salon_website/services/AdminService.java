package com.knyazev.beauty_salon_website.services;

import com.knyazev.beauty_salon_website.models.Craftsmen;
import com.knyazev.beauty_salon_website.models.Person;
import com.knyazev.beauty_salon_website.repositories.CraftsmenRepository;
import com.knyazev.beauty_salon_website.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final PersonRepository personRepository;
    private final CraftsmenRepository craftsmenRepository;

    @Autowired
    public AdminService(PersonRepository personRepository, CraftsmenRepository craftsmenRepository) {
        this.personRepository = personRepository;
        this.craftsmenRepository = craftsmenRepository;
    }

    public Iterable<Person> getAllPeople(){
        return (Iterable<Person>) personRepository.findAllUser();
    }

    public Iterable<Craftsmen> getAllMaster(){
          return (Iterable<Craftsmen>) craftsmenRepository.findAll();
    }
}
