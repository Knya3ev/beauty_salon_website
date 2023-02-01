package com.knyazev.beauty_salon_website.services;

import com.knyazev.beauty_salon_website.models.Craftsmen;
import com.knyazev.beauty_salon_website.repositories.MakeAnAppointmentRepository;
import com.knyazev.beauty_salon_website.support_classes.RenderCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MakeAnAppointmentService {
    private final MakeAnAppointmentRepository makeAnAppointmentRepository;

    private final CraftsmenService craftsmenService;

    private final int[] WORK_TIME = new int[]{9, 20}; // рабочие время с X до Y;

    @Autowired
    public MakeAnAppointmentService(MakeAnAppointmentRepository makeAnAppointmentRepository, CraftsmenService craftsmenService) {
        this.makeAnAppointmentRepository = makeAnAppointmentRepository;
        this.craftsmenService = craftsmenService;
    }

    private List<String> generateTimeList() {
        //В дальнейшем присвоить каждому мастеру свое рабочие время по умолчанию будет использоваться WORK_TIME;
        List<String> list = new ArrayList<>();
        for (int i = WORK_TIME[0]; i < WORK_TIME[1]; i++) list.add(Integer.toString(i) + ":00");
        return list;
    }

    public List<String> getActualityTimeForCraftsmen(long id_master, String date) {
        List<String> timeList = this.generateTimeList();
        //TODO: проверка времени у мастера

        return timeList;
    }






}
