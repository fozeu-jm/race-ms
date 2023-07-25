package fr.pmu.usecases;

import fr.pmu.boundaries.input.AddStarterToRacePort;
import fr.pmu.boundaries.output.RaceDsGateway;
import fr.pmu.domain.entity.Race;
import fr.pmu.domain.entity.Starter;
import fr.pmu.domain.factories.StarterFactory;
import fr.pmu.model.request.StarterRequestModel;
import fr.pmu.model.response.RaceResponseModel;
import fr.pmu.model.response.StarterResponseModel;
import fr.pmu.usecases.exception.ValidationException;

import java.util.List;
import java.util.UUID;

public class AddStarterToRaceUseCase extends RaceUseCase implements AddStarterToRacePort {
    private final StarterFactory starterFactory;

    public AddStarterToRaceUseCase(RaceDsGateway raceDsGateway, StarterFactory starterFactory) {
        super(raceDsGateway);
        this.starterFactory = starterFactory;
    }

    @Override
    public RaceResponseModel addStarterToRace(UUID raceUuid, StarterRequestModel requestModel) {
        Race race = raceDsGateway.findByUuid(raceUuid);
        int starterNumber = race.getMaxStarterNumber() + 1;
        Starter starter = starterFactory.create(requestModel.getName(), starterNumber);
        if(race.getStarters().contains(starter)){
            throw  new ValidationException("Starters on the same race cannot have the same name !");
        }
        race = raceDsGateway.addStarter(raceUuid, starter);
        List<StarterResponseModel> starterResponseModels = race.getStarters().stream().map(s -> new StarterResponseModel(s.getName(), s.getNumber())).toList();
        return new RaceResponseModel(race.getUuid().toString(), race.getDate(), race.getName(), race.getNumber(), starterResponseModels);
    }
}
