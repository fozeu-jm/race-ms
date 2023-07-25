package fr.pmu.domain.factories;

import fr.pmu.domain.entity.Race;
import fr.pmu.domain.entity.RaceImpl;
import fr.pmu.domain.entity.Starter;

import java.time.LocalDate;
import java.util.List;

public class RaceFactoryImpl implements RaceFactory{
    @Override
    public Race create(LocalDate date, String name, int number, List<Starter> starters) {
        return new RaceImpl(date, name, number, starters);
    }
}
