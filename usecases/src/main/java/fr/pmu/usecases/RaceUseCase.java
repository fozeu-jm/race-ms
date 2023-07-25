package fr.pmu.usecases;

import fr.pmu.boundaries.output.RaceDsGateway;

import java.time.LocalDate;
import java.util.UUID;

public abstract class RaceUseCase {
    protected final RaceDsGateway raceDsGateway;

    protected RaceUseCase(RaceDsGateway raceDsGateway) {
        this.raceDsGateway = raceDsGateway;
    }

    protected boolean RaceWithSameNameOrNumberAlreadyExistOnGivenDate(LocalDate date, String name, int number, UUID raceUuid){
        return raceDsGateway.existsByNameAndDateUuidNot(name, date, raceUuid) ||
                raceDsGateway.existsByNumberAndDateAndUuidNot(number, date, raceUuid);
    }
}
