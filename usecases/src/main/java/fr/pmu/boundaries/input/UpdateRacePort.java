package fr.pmu.boundaries.input;

import fr.pmu.model.request.RaceUpdateModel;
import fr.pmu.model.response.RaceResponseModel;

import java.util.UUID;

public interface UpdateRacePort {
    RaceResponseModel update(UUID raceUuid, RaceUpdateModel updateModel);
}
