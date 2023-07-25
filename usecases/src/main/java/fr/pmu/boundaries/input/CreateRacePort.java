package fr.pmu.boundaries.input;

import fr.pmu.model.request.RaceRequestModel;
import fr.pmu.model.response.RaceResponseModel;
import fr.pmu.usecases.exception.ValidationException;

public interface CreateRacePort {
    RaceResponseModel create(RaceRequestModel requestModel) throws ValidationException;
}
