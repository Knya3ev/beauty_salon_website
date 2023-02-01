package com.knyazev.beauty_salon_website.services;

import com.knyazev.beauty_salon_website.models.Craftsmen;
import com.knyazev.beauty_salon_website.models.DaysOfTheCraftsmen;
import com.knyazev.beauty_salon_website.repositories.DaysOfTheCraftsmenRepository;
import com.knyazev.beauty_salon_website.support_classes.RenderCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class SchedulingService {
    private final CraftsmenService craftsmenService;
    private final DaysOfTheCraftsmenService daysOfTheCraftsmenService;
    private final DaysOfTheCraftsmenRepository daysOfTheCraftsmenRepository;


    private final String[] MONTHS_OF_THE_YEAR_RU = new String[]{
            "Январь", "Февраль", "Март", "Арель", "Май", "Июнь", "Июль",
            "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабь"};
    private final List<String> DAY_OF_WEEK_RU = Arrays.asList("пн", "вт", "ср", "чт", "пт", "сб", "вс");

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy");
    private final SimpleDateFormat renderData = new SimpleDateFormat("MMM  (E)  dd-MM-yyyy");
    private final SimpleDateFormat daysOfWeek = new SimpleDateFormat("EEE");

    @Autowired
    public SchedulingService(CraftsmenService craftsmenService, DaysOfTheCraftsmenService daysOfTheCraftsmenService, DaysOfTheCraftsmenRepository daysOfTheCraftsmenRepository) {
        this.craftsmenService = craftsmenService;
        this.daysOfTheCraftsmenService = daysOfTheCraftsmenService;
        this.daysOfTheCraftsmenRepository = daysOfTheCraftsmenRepository;
    }


    private Date getDateNow() {
        Calendar calendar = GregorianCalendar.getInstance();
        return (Date) calendar.getTime();
    }
    public String getNameMonth (String whichMonth){
        Calendar calendar = GregorianCalendar.getInstance();
        int index = whichMonth.equals("current")? calendar.get(Calendar.MONTH) : calendar.get(Calendar.MONTH) + 1;
        return MONTHS_OF_THE_YEAR_RU[index];
    }

    private List<List<RenderCalendar>> generateCalendar(Calendar calendar,
                                                        int day,
                                                        int currentMonth,
                                                        List<RenderCalendar> week,
                                                        List<List<RenderCalendar>> month,
                                                        List<String> listWorkdaysCraftsmen) {
        if (month.isEmpty()) for (int i = 0; i < day; i++) week.add(new RenderCalendar());

        while (currentMonth == calendar.get(Calendar.MONTH)) {
            if (week.size() > 6) {
                month.add(cloneList(week));
                week.clear();
            }
            week.add(
                    this.checkDisabledData(
                            new RenderCalendar(
                                    calendar.get(Calendar.DAY_OF_MONTH),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.YEAR)),
                            listWorkdaysCraftsmen));

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        if (!week.isEmpty()) month.add(week);
        return month;
    }

    public List<List<RenderCalendar>> getDaysOfTheCurrentMonth(List<DaysOfTheCraftsmen> list, String whichMonth) {
        Calendar calendar = GregorianCalendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        int day = DAY_OF_WEEK_RU.indexOf(daysOfWeek.format(calendar.getTime()));
        List<RenderCalendar> week = new ArrayList<>();
        List<List<RenderCalendar>> month = new ArrayList<>();
        List<String> listWorkdaysCraftsmen = this.rewriteDateFormat(list);

        if (whichMonth.equals("next")) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.MONTH, 1);
            currentMonth = currentMonth + 1;
        }

        return this.generateCalendar(calendar, day, currentMonth, week, month, listWorkdaysCraftsmen);
    }

    private RenderCalendar checkDisabledData(RenderCalendar renderCalendar, List<String> workdaysCraftsmen) {
        if (workdaysCraftsmen.isEmpty()) return renderCalendar;

        else {
            if (workdaysCraftsmen.contains(renderCalendar.getData())) renderCalendar.setDisabled(true);
            return renderCalendar;
        }
    }

    private List<RenderCalendar> cloneList(List<RenderCalendar> list) {
        List<RenderCalendar> clone = new ArrayList<>(list.size());
        for (RenderCalendar obj : list) clone.add(obj.clone());
        return clone;
    }

    public List<String> rewriteDateFormat(List<DaysOfTheCraftsmen> list) {
        List<String> listString = new ArrayList<>();
        for (DaysOfTheCraftsmen data : list) listString.add(simpleDateFormat.format(data.getDaysWork()));
        return listString;
    }


    public void checkingActualityData(List<DaysOfTheCraftsmen> dateList) {
        if (!dateList.isEmpty()) {
            //TODO: переписать на обычный запрос к бд  .Before()  с получением всех предыдущих дат относительно current
            dateList = daysOfTheCraftsmenService.sortData(dateList);

            Date nawData = this.getDateNow();
            int i = 0; // предосторожность в непредсказуемом случае цикл закончится через 2 итерации
            while (i < 2) {
                if (dateList.isEmpty()) break;
                if (dateList.get(0).getDaysWork().after(nawData)) break;
                if (dateList.get(0).getDaysWork().before(nawData)
                        && dateList.get(0).getDaysWork().getDate() < nawData.getDate()) {
                    daysOfTheCraftsmenService.removeNotActualityData(dateList.get(0));
                } else i++;
            }

        }
    }
}
