package fr.pmu.domain.factories;

import fr.pmu.domain.entity.Starter;
import fr.pmu.domain.entity.StarterImpl;

public class StarterFactoryImpl implements StarterFactory {
    @Override
    public Starter create(String name, int number) {
        return new StarterImpl(name, number);
    }
}
