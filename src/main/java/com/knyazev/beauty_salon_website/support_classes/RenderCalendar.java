package com.knyazev.beauty_salon_website.support_classes;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class RenderCalendar implements Cloneable {
    private int day;
    private int month;
    private int year;

    private List<String> timeList = new ArrayList<>();

    private boolean actuality = true;
    private boolean disabled = false;

    public RenderCalendar() {
        this.day = 0;
        this.month = 0;
        this.year = 0;

        this.actuality = false;
    }

    public RenderCalendar(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;

    }

    public String getData() {
        return Integer.toString(day) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(year);
    }

    @Override
    public RenderCalendar clone() {
        try {
            RenderCalendar clone = (RenderCalendar) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
