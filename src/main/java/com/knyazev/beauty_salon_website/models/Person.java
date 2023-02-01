package com.knyazev.beauty_salon_website.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Это поле не может быть пустым!")
    @Size(min = 2,max = 20, message = "не может быть короче чем 2 символа и больше чем 20 символов")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Это поле не может быть пустым!")
    @Size(min = 2,max = 20, message = "не может быть короче чем 2 символа и больше чем 20 символов")
    @Column(name = "last_name")
    private String lastName;
    @NotEmpty(message = "Это поле не может быть пустым!")
    @Size(min = 2,max = 20, message = "не может быть короче чем 2 символа и больше чем 20 символов")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Email не может быть пустым")
    @Email(message = "не валидный email")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Укажите свой мобильный телефон ")
    @Size(min = 11,max = 11, message = "телефон не может быть меньше 11 символов")
    @Column(name = "number_phone")
    private String numberPhone;

    @NotEmpty(message = "укажите пароль")
    @Column(name = "password")
    private String password;

    @Column(name = "age")
    private int age;

    @Column(name = "role")
    private String role;

    @OneToOne(mappedBy = "person", cascade = {CascadeType.ALL})
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Craftsmen craftsmen;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "make_an_appointment",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "craftsmen_id")
    )
    private List<Craftsmen> craftsmenList;

    public Person() {
    }

    public Person(String firstName, String lastName, String email, String numberPhone, String password, int age, Craftsmen craftsmen) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.numberPhone = numberPhone;
        this.password = password;
        this.age = age;
        this.craftsmen = craftsmen;
        this.craftsmenList =new ArrayList<>();
    }

    public void addCraftsmen(Craftsmen craftsmen){
        craftsmenList.add(craftsmen);
        craftsmen.getPersonList().add(this);
    }
    public void removeCraftsmen(Craftsmen craftsmen){
        craftsmenList.remove(craftsmen);
        craftsmen.getPersonList().remove(this);
    }


    public void setCraftsmen(Craftsmen craftsmen) {
        this.craftsmen = craftsmen;
        craftsmen.setPerson(this);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", numberPhone='" + numberPhone + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", role='" + role + '\'' +
                ", craftsman=" + craftsmen +
                '}';
    }
}
