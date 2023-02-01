package com.knyazev.beauty_salon_website.repositories;

import com.knyazev.beauty_salon_website.models.MakeAnAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakeAnAppointmentRepository extends JpaRepository<MakeAnAppointment, Long> {

//    Iterable<MakeAnAppointment>findByData();
}
