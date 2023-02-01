package com.knyazev.beauty_salon_website.repositories;

import com.knyazev.beauty_salon_website.models.DaysOfTheCraftsmen;
import com.knyazev.beauty_salon_website.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Optional;


@Repository
public interface DaysOfTheCraftsmenRepository extends JpaRepository<DaysOfTheCraftsmen, Long> {

    Optional<DaysOfTheCraftsmen> findByDaysWork(Date daysWork);
}
