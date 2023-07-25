package fr.pmu.domain.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface Race {
    String getName();
    int getNumber();
    LocalDate getDate();
    void setName(String name);
    void setNumber(int number);
    void setDate(LocalDate date);
    UUID getUuid();
    void setUuid(UUID uuid);
    void setStarters(List<Starter> starters);
    List<Starter> getStarters();
    int getMaxStarterNumber();
    void addStarter(Starter starter);
    void removeStarter(Starter starter);
    Starter getStarter(String name);

}
