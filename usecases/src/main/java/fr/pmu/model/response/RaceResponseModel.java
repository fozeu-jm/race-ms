package fr.pmu.model.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RaceResponseModel {
    private String uuid;
    private LocalDate date;
    private String name;
    private int number;
    private List<StarterResponseModel> starters = new ArrayList<>();

    public RaceResponseModel(String uuid, LocalDate date, String name, int number, List<StarterResponseModel> starters) {
        this.uuid = uuid;
        this.date = date;
        this.name = name;
        this.number = number;
        this.starters = starters;
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

    public List<StarterResponseModel> getStarters() {
        return starters;
    }

    public void setStarters(List<StarterResponseModel> starters) {
        this.starters = starters;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
