package com.knyazev.beauty_salon_website.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "craftsmen")
public class Craftsmen {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "укажите специализацию мастера ")
    @Column(name = "specialization") //TODO: в дальнейшем создать бд с специализациями ONE TO MANY

    private  String specialization;

    @Column(name = "infoAboutMaster")
    private  String infoAboutMaster;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;


    @ManyToMany(mappedBy = "craftsmenList")
    private List<Person> personList;

    @ManyToMany(mappedBy = "craftsmenList")
    private List<DaysOfTheCraftsmen> workdays;



    public Craftsmen() {
    }

    public Craftsmen(Long id, String specialization, String infoAboutMaster, Person person) {
        this.id = id;
        this.specialization = specialization;
        this.infoAboutMaster = infoAboutMaster;
        this.person = person;
        this.personList= new ArrayList<>();
    }

    public void addPerson(Person person){
        personList.add(person);
        person.getCraftsmenList().add(this);
    }
    public void removePerson (Person person){
        personList.remove(person);
        person.getCraftsmenList().remove(this);
    }

    public void addWorkDays(DaysOfTheCraftsmen data){
        workdays.add(data);
        data.getCraftsmenList().add(this);
    }

    public void removeWorkDays(DaysOfTheCraftsmen data){
        workdays.remove(data);
        data.getCraftsmenList().remove(this);
    }


}
