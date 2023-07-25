package fr.pmu.model.request;

import fr.pmu.domain.entity.Race;

import java.time.LocalDate;

public class RaceUpdateModel {
    private LocalDate date;
    private String name;
    private int number;

    public RaceUpdateModel(LocalDate date, String name, int number) {
        this.date = date;
        this.name = name;
        this.number = number;
    }

    public RaceUpdateModel() {

    }

    public void updateEntity(Race race){
        race.setDate(date);
        race.setName(name);
        race.setNumber(number);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
