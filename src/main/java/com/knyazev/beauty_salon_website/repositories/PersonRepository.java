package com.knyazev.beauty_salon_website.repositories;

import com.knyazev.beauty_salon_website.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    Optional<Person> findByUsername(String username);
    Optional<Person> findByEmail(String email);
    Optional<Person> findByNumberPhone(String numberPhone);

    @Query(value = "SELECT p FROM Person p WHERE p.role = 'ROLE_USER'")
    Iterable<Person> findAllUser();

    @Query(value = "SELECT p FROM Person p WHERE p.role = 'ROLE_CRAFTSMEN'")
    Iterable<Person> findAllMaster();
}
