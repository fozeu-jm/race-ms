package fr.pmu.boundaries.output;

import fr.pmu.domain.entity.Race;
import fr.pmu.domain.entity.Starter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RaceDsGateway {
    Race findByUuid(UUID raceUuid);
    boolean existsByNameAndDateUuidNot(String name, LocalDate date, UUID raceUuid);
    boolean existsByNumberAndDateAndUuidNot(int number, LocalDate date, UUID raceUuid);
    void save(Race race);
    void update(UUID raceUuid, Race race);
    void delete(Race race);
    List<Race> findAll();
    Race addStarter(UUID raceUuid, Starter starter);
    Race removeStarter(UUID raceUuid, Starter starter);

}
