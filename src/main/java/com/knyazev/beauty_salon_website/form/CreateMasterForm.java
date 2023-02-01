package com.knyazev.beauty_salon_website.form;

import com.knyazev.beauty_salon_website.models.Craftsmen;
import com.knyazev.beauty_salon_website.models.Person;

import javax.validation.Valid;


public class CreateMasterForm {
    @Valid
    private Person person;
    @Valid
    private Craftsmen craftsmen;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Craftsmen getCraftsmen() {
        return craftsmen;
    }

    public void setCraftsmen(Craftsmen craftsmen) {
        this.craftsmen = craftsmen;
    }
}
