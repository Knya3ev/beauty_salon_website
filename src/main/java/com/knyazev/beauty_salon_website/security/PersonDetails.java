package com.knyazev.beauty_salon_website.security;

import com.knyazev.beauty_salon_website.models.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class PersonDetails implements UserDetails {
    private final Person person;

    public PersonDetails(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }
    public String getFirstName(){
        return this.person.getFirstName();
    }
    public String getLastName(){
        return this.person.getLastName();
    }
    public String getNumberPhone(){
        return this.person.getNumberPhone();
    }
    public String getEmail(){
        return  this.person.getEmail();
    }
    public String getRole(){
        return this.person.getRole();
    }




    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true ;
    }

    public Person getPerson(){
        return this.person;
    }
}
