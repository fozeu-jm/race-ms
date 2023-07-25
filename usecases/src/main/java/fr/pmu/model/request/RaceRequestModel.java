package fr.pmu.model.request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RaceRequestModel {
    private LocalDate date;
    private String name;
    private int number;
    private List<StarterRequestModel> starters = new ArrayList<>();

    public RaceRequestModel(LocalDate date, String name, int number, List<StarterRequestModel> starters) {
        this.date = date;
        this.name = name;
        this.number = number;
        this.starters = starters;
    }

    public RaceRequestModel() {

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

    public List<StarterRequestModel> getStarters() {
        return starters;
    }

    public void setStarters(List<StarterRequestModel> starters) {
        this.starters = starters;
    }
}
