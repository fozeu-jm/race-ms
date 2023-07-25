package fr.pmu.usecases;

import fr.pmu.boundaries.input.RemoveStarterFromRacePort;
import fr.pmu.boundaries.output.RaceDsGateway;
import fr.pmu.domain.entity.Race;
import fr.pmu.domain.entity.Starter;
import fr.pmu.domain.factories.StarterFactory;
import fr.pmu.model.request.StarterRequestModel;
import fr.pmu.model.response.RaceResponseModel;
import fr.pmu.model.response.StarterResponseModel;
import fr.pmu.usecases.exception.ValidationException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class RemoveStarterFromRaceUseCase extends RaceUseCase implements RemoveStarterFromRacePort {
    private final StarterFactory starterFactory;
    public RemoveStarterFromRaceUseCase(RaceDsGateway raceDsGateway, StarterFactory starterFactory) {
        super(raceDsGateway);
        this.starterFactory = starterFactory;
    }

    @Override
    public RaceResponseModel removeStarterFromRacePort(UUID raceUuid, StarterRequestModel requestModel) {
        Race race = raceDsGateway.findByUuid(raceUuid);
        Starter starter = race.getStarter(requestModel.getName());
        if(Objects.isNull(starter)){
            throw new ValidationException("Starter does not exist on this race !");
        }
        race = raceDsGateway.removeStarter(raceUuid, starter);
        List<StarterResponseModel> starterResponseModels = race.getStarters().stream().map(s -> new StarterResponseModel(s.getName(), s.getNumber())).toList();
        return new RaceResponseModel(race.getUuid().toString(), race.getDate(), race.getName(), race.getNumber(), starterResponseModels);
    }
}
