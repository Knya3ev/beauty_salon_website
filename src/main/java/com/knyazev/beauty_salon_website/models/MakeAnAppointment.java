package com.knyazev.beauty_salon_website.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "make_an_appointment")
public class MakeAnAppointment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "data_record")
    @Temporal(TemporalType.DATE)
    private Date data;

    @NotEmpty
    @Column(name = "time_record")
    @Temporal(TemporalType.TIME)
    private Date time;

    @NotEmpty
    @Column(name = "beauty_service")
    private String beautyService;

    @NotEmpty
    @Column(name = "person_id")
    private int personId;

    @NotEmpty
    @Column(name = "craftsmen_id")
    private int craftsmenId;

    public MakeAnAppointment() {
    }

    public MakeAnAppointment(Date data, Date time, String beautyService, int personId, int craftsmen_id) {
        this.data = data;
        this.time = time;
        this.beautyService = beautyService;
        this.personId = personId;
        this.craftsmenId = craftsmen_id;
    }

}
