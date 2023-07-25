package fr.pmu.gateways.sql;

import fr.pmu.annotation.Produce;
import fr.pmu.boundaries.output.RaceDsGateway;
import fr.pmu.domain.entity.Race;
import fr.pmu.domain.entity.RaceImpl;
import fr.pmu.domain.entity.Starter;
import fr.pmu.domain.entity.StarterImpl;
import fr.pmu.exceptions.ResourceNotFoundException;
import fr.pmu.gateways.event.EventBusGateway;
import fr.pmu.gateways.mapper.RaceJpaMapper;
import fr.pmu.gateways.mapper.StarterJpaMapper;
import fr.pmu.repositories.RaceRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RaceSqlGateway implements RaceDsGateway {
    private final RaceRepository raceRepository;

    public RaceSqlGateway(RaceRepository raceRepository, EventBusGateway eventBus) {
        this.raceRepository = raceRepository;
    }

    @Override
    public Race findByUuid(UUID raceUuid) {
        RaceJpaMapper raceJpaMapper = raceRepository.findByUuid(raceUuid.toString())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Race with uuid %s not found", raceUuid)));
        Race race = new RaceImpl();
        jpaMapperToEntity(raceJpaMapper, race);
        return race;
    }

    @Override
    public List<Race> findAll() {
        List<RaceJpaMapper> raceJpaMappers = raceRepository.findAll();
        if (raceJpaMappers.isEmpty()) {
            return new ArrayList<>();
        }
        return raceJpaMappers.stream().map(r -> {
            Race race = new RaceImpl();
            jpaMapperToEntity(r, race);
            return race;
        }).toList();
    }

    @Override
    @Produce
    public void save(Race race) {
        RaceJpaMapper raceJpaMapper = new RaceJpaMapper();
        entityToJpaMapper(race, raceJpaMapper);
        raceRepository.save(raceJpaMapper);
    }

    @Override
    public void update(UUID raceUuid, Race race) {
        RaceJpaMapper raceJpaMapper = raceRepository.findByUuid(raceUuid.toString())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Race with uuid %s not found", raceUuid)));
        entityToJpaMapper(race, raceJpaMapper);
        raceRepository.save(raceJpaMapper);
    }

    @Override
    public void delete(Race race) {
        RaceJpaMapper raceJpaMapper = raceRepository.findByUuid(race.getUuid().toString())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Race with uuid %s not found", race.getUuid())));
        raceRepository.delete(raceJpaMapper);
    }

    @Override
    public boolean existsByNameAndDateUuidNot(String name, LocalDate date, UUID raceUuid) {
        return raceRepository.existsByNameAndDateAndUuidNot(name, date, raceUuid.toString());
    }

    @Override
    public boolean existsByNumberAndDateAndUuidNot(int number, LocalDate date, UUID raceUuid) {
        return raceRepository.existsByNumberAndDateAndUuidNot(number, date, raceUuid.toString());
    }

    @Override
    public Race addStarter(UUID raceUuid, Starter starter) {
        RaceJpaMapper raceJpaMapper = raceRepository.findByUuid(raceUuid.toString())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Race with uuid %s not found", raceUuid)));
        raceJpaMapper.addStarters(Collections.singletonList(new StarterJpaMapper(starter.getName(), starter.getNumber())));
        raceRepository.save(raceJpaMapper);
        Race race = new RaceImpl();
        jpaMapperToEntity(raceJpaMapper, race);
        return race;
    }

    @Override
    public Race removeStarter(UUID raceUuid, Starter starter) {
        RaceJpaMapper raceJpaMapper = raceRepository.findByUuid(raceUuid.toString())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Race with uuid %s not found", raceUuid)));
        raceJpaMapper.removeStarter(new StarterJpaMapper(starter.getName(), starter.getNumber()));
        raceRepository.save(raceJpaMapper);
        Race race = new RaceImpl();
        jpaMapperToEntity(raceJpaMapper, race);
        return race;
    }

    private void jpaMapperToEntity(RaceJpaMapper raceJpaMapper, Race race) {
        race.setUuid(UUID.fromString(raceJpaMapper.getUuid()));
        race.setDate(raceJpaMapper.getDate());
        race.setName(raceJpaMapper.getName());
        race.setNumber(raceJpaMapper.getNumber());
        race.setStarters(raceJpaMapper.getStarters().stream().map(r -> (Starter) new StarterImpl(r.getName(), r.getNumber())).toList());
    }

    private void entityToJpaMapper(Race race, RaceJpaMapper raceJpaMapper) {
        raceJpaMapper.setUuid(race.getUuid().toString());
        raceJpaMapper.setDate(race.getDate());
        raceJpaMapper.setName(race.getName());
        raceJpaMapper.setNumber(race.getNumber());
        raceJpaMapper.addStarters(race.getStarters().stream().map(r -> new StarterJpaMapper(r.getName(), r.getNumber())).toList());
    }
}
