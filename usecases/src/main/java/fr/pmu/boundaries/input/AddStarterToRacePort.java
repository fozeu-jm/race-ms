package fr.pmu.boundaries.input;

import fr.pmu.model.request.StarterRequestModel;
import fr.pmu.model.response.RaceResponseModel;

import java.util.UUID;

public interface AddStarterToRacePort {
    RaceResponseModel addStarterToRace(UUID raceUuid, StarterRequestModel requestModel);
}
