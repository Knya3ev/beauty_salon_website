package com.knyazev.beauty_salon_website.models;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "days_of_the_craftsmen")
public class DaysOfTheCraftsmen {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "days_work")
    private Date daysWork;


    @ManyToMany(cascade = {CascadeType.REMOVE})
    @JoinTable(
            name = "days_work",
            joinColumns = @JoinColumn(name = "days_of_the_craftsmen_id"),
            inverseJoinColumns = @JoinColumn(name = "craftsmen_id")
    )
    private List<Craftsmen> craftsmenList =  new ArrayList<>();

    public DaysOfTheCraftsmen() {
    }

    public DaysOfTheCraftsmen(Date daysWork) {
        this.daysWork = daysWork;
    }

    public void removeDataToCraftsmen(Craftsmen craftsmen){
        craftsmenList.remove(craftsmen);
        craftsmen.getWorkdays().remove(this);
    }

}

