package fr.pmu.domain.factories;

import fr.pmu.domain.entity.Race;
import fr.pmu.domain.entity.Starter;

import java.time.LocalDate;
import java.util.List;

public interface RaceFactory {
    Race create(LocalDate date, String name, int number, List<Starter> starters);
}
