package fr.pmu.usecases;

import fr.pmu.boundaries.input.CreateRacePort;
import fr.pmu.boundaries.output.RaceDsGateway;
import fr.pmu.domain.entity.Race;
import fr.pmu.domain.entity.Starter;
import fr.pmu.domain.factories.RaceFactory;
import fr.pmu.domain.factories.StarterFactory;
import fr.pmu.model.request.RaceRequestModel;
import fr.pmu.model.request.StarterRequestModel;
import fr.pmu.model.response.RaceResponseModel;
import fr.pmu.model.response.StarterResponseModel;
import fr.pmu.usecases.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class CreateRaceUseCase extends RaceUseCase implements CreateRacePort {
    private final RaceFactory raceFactory;
    private final StarterFactory starterFactory;

    public CreateRaceUseCase(RaceDsGateway raceDsGateway, RaceFactory raceFactory, StarterFactory starterFactory) {
        super(raceDsGateway);
        this.raceFactory = raceFactory;
        this.starterFactory = starterFactory;
    }


    @Override
    public RaceResponseModel create(RaceRequestModel requestModel) throws ValidationException {
        List<Starter> starters = getStartersListFromRaceRequest(requestModel);
        Race race = raceFactory.create(requestModel.getDate(), requestModel.getName(), requestModel.getNumber(), starters);
        if (RaceWithSameNameOrNumberAlreadyExistOnGivenDate(requestModel.getDate(), requestModel.getName(), requestModel.getNumber(), race.getUuid())) {
            throw new ValidationException("Race with same name or number already exist on given date !");
        }
        raceDsGateway.save(race);
        List<StarterResponseModel> starterResponseModels = race.getStarters().stream().map(s -> new StarterResponseModel(s.getName(), s.getNumber())).toList();
        return new RaceResponseModel(race.getUuid().toString(), race.getDate(), race.getName(), race.getNumber(), starterResponseModels);
    }

    private List<Starter> getStartersListFromRaceRequest(RaceRequestModel requestModel){
        if(requestModel.getStarters().size() < 3){
            throw new ValidationException("Race must have at least 3 starters");
        }
        List<Starter> starters = new ArrayList<>();
        for(int i = 0; i < requestModel.getStarters().size(); i++){
            StarterRequestModel starterRm = requestModel.getStarters().get(i);
            Starter starter = starterFactory.create(starterRm.getName(), i+1);
            if(starters.contains(starter)){
                throw  new ValidationException("Starters on the same race cannot have the same name !");
            }
            starters.add(starter);
        }
        return starters;
    }
}
