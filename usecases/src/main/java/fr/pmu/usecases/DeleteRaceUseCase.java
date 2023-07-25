package fr.pmu.usecases;

import fr.pmu.boundaries.input.DeleteRacePort;
import fr.pmu.boundaries.output.RaceDsGateway;
import fr.pmu.domain.entity.Race;

import java.util.UUID;

public class DeleteRaceUseCase extends RaceUseCase implements DeleteRacePort {
    public DeleteRaceUseCase(RaceDsGateway raceDsGateway) {
        super(raceDsGateway);
    }

    @Override
    public boolean delete(UUID raceUuid) {
        Race race = raceDsGateway.findByUuid(raceUuid);
        raceDsGateway.delete(race);
        return true;
    }
}
