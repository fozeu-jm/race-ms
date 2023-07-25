package fr.pmu.boundaries.input;

import fr.pmu.model.response.RaceResponseModel;

import java.util.UUID;

public interface FindRaceByUuidPort {
    RaceResponseModel findByUuid(UUID raceUuid);
}
