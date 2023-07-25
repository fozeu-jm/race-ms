package fr.pmu.domain.factories;

import fr.pmu.domain.entity.Starter;

public interface StarterFactory {
    Starter create(String name, int number);
}
