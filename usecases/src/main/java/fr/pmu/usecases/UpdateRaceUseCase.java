package fr.pmu.usecases;

import fr.pmu.boundaries.input.UpdateRacePort;
import fr.pmu.boundaries.output.RaceDsGateway;
import fr.pmu.domain.entity.Race;
import fr.pmu.domain.factories.RaceFactory;
import fr.pmu.model.request.RaceUpdateModel;
import fr.pmu.model.response.RaceResponseModel;
import fr.pmu.model.response.StarterResponseModel;
import fr.pmu.usecases.exception.ValidationException;

import java.util.List;
import java.util.UUID;

public class UpdateRaceUseCase extends RaceUseCase implements UpdateRacePort {
    private final RaceFactory raceFactory;

    public UpdateRaceUseCase(RaceDsGateway raceDsGateway, RaceFactory raceFactory) {
        super(raceDsGateway);
        this.raceFactory = raceFactory;
    }


    @Override
    public RaceResponseModel update(UUID raceUuid, RaceUpdateModel updateModel) {
        Race race = raceDsGateway.findByUuid(raceUuid);
        if (RaceWithSameNameOrNumberAlreadyExistOnGivenDate(updateModel.getDate(), updateModel.getName(), updateModel.getNumber(), raceUuid)) {
            throw new ValidationException("Race with same name or number already exist on given date !");
        }
        updateModel.updateEntity(race);
        raceDsGateway.update(raceUuid, race);
        List<StarterResponseModel> starterResponseModels = race.getStarters().stream().map(s -> new StarterResponseModel(s.getName(), s.getNumber())).toList();
        return new RaceResponseModel(race.getUuid().toString(), race.getDate(), race.getName(), race.getNumber(), starterResponseModels);
    }
}
