package com.knyazev.beauty_salon_website.util;

import com.knyazev.beauty_salon_website.form.CreateMasterForm;
import com.knyazev.beauty_salon_website.models.Craftsmen;
import com.knyazev.beauty_salon_website.models.Person;
import com.knyazev.beauty_salon_website.services.PersonDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class CreateMasterFormValidator implements Validator {
    private final PersonDetailsService personDetailsService;


    public CreateMasterFormValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return CreateMasterForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateMasterForm form = (CreateMasterForm) target;
        Person person = form.getPerson();

        Craftsmen master = form.getCraftsmen();

        personDetailsService.validPeron(person, errors,
                "person.username","person.email",
                "person.numberPhone", "person.age" );

    }
}
