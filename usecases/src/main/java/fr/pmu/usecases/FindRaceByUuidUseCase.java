package fr.pmu.usecases;

import fr.pmu.boundaries.input.FindRaceByUuidPort;
import fr.pmu.boundaries.output.RaceDsGateway;
import fr.pmu.domain.entity.Race;
import fr.pmu.model.response.RaceResponseModel;
import fr.pmu.model.response.StarterResponseModel;

import java.util.List;
import java.util.UUID;

public class FindRaceByUuidUseCase extends RaceUseCase implements FindRaceByUuidPort {
    public FindRaceByUuidUseCase(RaceDsGateway raceDsGateway) {
        super(raceDsGateway);
    }

    @Override
    public RaceResponseModel findByUuid(UUID raceUuid) {
        Race race = raceDsGateway.findByUuid(raceUuid);
        List<StarterResponseModel> starterResponseModels = race.getStarters().stream().map(s -> new StarterResponseModel(s.getName(), s.getNumber())).toList();
        return new RaceResponseModel(race.getUuid().toString(), race.getDate(), race.getName(), race.getNumber(), starterResponseModels);
    }
}
