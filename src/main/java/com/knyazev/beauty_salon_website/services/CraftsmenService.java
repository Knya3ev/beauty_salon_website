package com.knyazev.beauty_salon_website.services;


import com.knyazev.beauty_salon_website.models.Craftsmen;
import com.knyazev.beauty_salon_website.models.Person;
import com.knyazev.beauty_salon_website.repositories.CraftsmenRepository;
import com.knyazev.beauty_salon_website.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CraftsmenService {
    private final PasswordEncoder passwordEncoder;

    private final PersonRepository personRepository;
    private final CraftsmenRepository craftsmenRepository;


    @Autowired
    public CraftsmenService(PasswordEncoder passwordEncoder, PersonRepository personRepository,
                            CraftsmenRepository craftsmenRepository) {
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
        this.craftsmenRepository = craftsmenRepository;
    }

    public List<Craftsmen> getAllMaster() {
        return craftsmenRepository.findAll();
    }

    public Optional<Craftsmen> getMasterId(long id) {
        return craftsmenRepository.findById(id);
    }

    @Transactional
    public void addMaster(Person person, Craftsmen craftsmen) {
        person.setCraftsmen(craftsmen);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_CRAFTSMEN");
        personRepository.save(person);
    }

    public Craftsmen getCraftsmen(long id) {
        Optional<Craftsmen> craftsmen = craftsmenRepository.findById(id);
        if (!craftsmen.isEmpty()) return craftsmen.get();
        else return null;

    }
}
