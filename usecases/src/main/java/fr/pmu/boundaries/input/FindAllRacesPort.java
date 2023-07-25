package fr.pmu.boundaries.input;

import fr.pmu.model.response.RaceResponseModel;

import java.util.List;

public interface FindAllRacesPort {
    List<RaceResponseModel> findAll();
}
