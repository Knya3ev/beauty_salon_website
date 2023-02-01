package com.knyazev.beauty_salon_website.repositories;

import com.knyazev.beauty_salon_website.models.Craftsmen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CraftsmenRepository extends JpaRepository<Craftsmen,Long> {

}
