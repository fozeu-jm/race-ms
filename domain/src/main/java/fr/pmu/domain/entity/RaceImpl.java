package fr.pmu.domain.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class RaceImpl implements Race, Serializable {
    @Serial
    private static final long serialVersionUID = -1352143730889566787L;
    private UUID uuid = UUID.randomUUID();
    private LocalDate date;
    private String name;
    private int number;
    private List<Starter> starters = new ArrayList<>();

    public RaceImpl(LocalDate date, String name, int number, List<Starter> starters) {
        this.date = date;
        this.name = name;
        this.number = number;
        this.starters = starters;
    }

    public RaceImpl() {

    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public List<Starter> getStarters() {
        return starters;
    }

    public void setStarters(List<Starter> starters) {
        this.starters = starters;
    }

    public int getMaxStarterNumber() {
        return Objects.requireNonNull(starters.stream().
                max(Comparator.comparingInt(Starter::getNumber))
                .orElse(null)).getNumber();
    }

    public void addStarter(Starter starter){
        starters.add(starter);
    }

    public Starter getStarter(String name){
        return starters.stream().filter(s -> Objects.equals(s.getName(), name)).findFirst().orElse(null);
    }

    public void removeStarter(Starter starter){
        starters.remove(starter);
    }
}
