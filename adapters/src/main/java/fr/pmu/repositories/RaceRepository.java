package fr.pmu.repositories;

import fr.pmu.gateways.mapper.RaceJpaMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface RaceRepository extends JpaRepository<RaceJpaMapper, Long> {
    Optional<RaceJpaMapper> findByUuid(String uuid);
    boolean existsByNameAndDateAndUuidNot(String name, LocalDate date, String uuid);
    boolean existsByNumberAndDateAndUuidNot(int number, LocalDate date, String uuid);
}
