package com.knyazev.beauty_salon_website.services;


import com.knyazev.beauty_salon_website.models.Craftsmen;
import com.knyazev.beauty_salon_website.models.DaysOfTheCraftsmen;
import com.knyazev.beauty_salon_website.repositories.DaysOfTheCraftsmenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DaysOfTheCraftsmenService {
    private final DaysOfTheCraftsmenRepository daysOfTheCraftsmenRepository;
    private final CraftsmenService craftsmenService;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy");

    @Autowired
    public DaysOfTheCraftsmenService(DaysOfTheCraftsmenRepository daysOfTheCraftsmenRepository, CraftsmenService craftsmenService) {
        this.daysOfTheCraftsmenRepository = daysOfTheCraftsmenRepository;
        this.craftsmenService = craftsmenService;
    }

    @Transactional
    private DaysOfTheCraftsmen saveData(Date data) {
        DaysOfTheCraftsmen daysOfTheCraftsmen = new DaysOfTheCraftsmen(data);
        daysOfTheCraftsmenRepository.save(daysOfTheCraftsmen);
        return daysOfTheCraftsmen;
    }

    private DaysOfTheCraftsmen checkData(Date data) {
        Optional<DaysOfTheCraftsmen> d = daysOfTheCraftsmenRepository.findByDaysWork(data);

        if (d.isEmpty()) return this.saveData(data);

        return d.get();
    }

    public List<DaysOfTheCraftsmen> sortData(List<DaysOfTheCraftsmen> dateList){
        Collections.sort(dateList, new Comparator<DaysOfTheCraftsmen>() {
            @Override
            public int compare(DaysOfTheCraftsmen o1, DaysOfTheCraftsmen o2) {
                if (o1 == null || o2 == null) return 0;

                return o1.getDaysWork().compareTo(o2.getDaysWork());
            }
        });
        return dateList;
    }

    public List<DaysOfTheCraftsmen> getDataObjects(List<String> list) throws ParseException {
        List<DaysOfTheCraftsmen> dataList = new ArrayList<>();
        for (String item : list) dataList.add(this.checkData(simpleDateFormat.parse(item)));
        return dataList;
    }

    public DaysOfTheCraftsmen getDataId(long id) {
        Optional<DaysOfTheCraftsmen> daysOfTheCraftsmen = daysOfTheCraftsmenRepository.findById(id);
        if (!daysOfTheCraftsmen.isEmpty()) return daysOfTheCraftsmen.get();
        else return null;
    }

    @Transactional
    public void removeData(long idData, long idCraftsmen) {
        Craftsmen craftsmen = craftsmenService.getCraftsmen(idCraftsmen);
        DaysOfTheCraftsmen date = this.getDataId(idData);

        if (date != null && craftsmen != null) remove(craftsmen,date);
    }

    @Transactional
    public void saveDataForCraftsmen(List<String> dataList, long idCraftsmen) throws ParseException {
        List<DaysOfTheCraftsmen> daysOfTheCraftsmenList = this.getDataObjects(dataList);
        Craftsmen craftsmen = craftsmenService.getCraftsmen(idCraftsmen);
        for (DaysOfTheCraftsmen days : daysOfTheCraftsmenList) craftsmen.addWorkDays(days);
    }

    @Transactional
    private void remove(Craftsmen craftsmen, DaysOfTheCraftsmen date) {
        //удаление из таблицы daysWorks
        date.removeDataToCraftsmen(craftsmen);
    }

    @Transactional
    public void removeNotActualityData(DaysOfTheCraftsmen notActualityDate) {
        List<Craftsmen> craftsmenList = notActualityDate.getCraftsmenList();

        while (true){ // удаление неактуальной даты у всех мастеров
            if(craftsmenList.isEmpty()) break;
            else this.remove(craftsmenList.get(0),notActualityDate);
        }

        daysOfTheCraftsmenRepository.delete(notActualityDate); // удаление даты из бд
    }


}


