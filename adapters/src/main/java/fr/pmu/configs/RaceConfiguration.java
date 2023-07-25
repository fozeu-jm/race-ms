package fr.pmu.configs;

import fr.pmu.boundaries.input.*;
import fr.pmu.boundaries.output.RaceDsGateway;
import fr.pmu.domain.factories.RaceFactory;
import fr.pmu.domain.factories.RaceFactoryImpl;
import fr.pmu.domain.factories.StarterFactory;
import fr.pmu.domain.factories.StarterFactoryImpl;
import fr.pmu.gateways.event.EventBusGateway;
import fr.pmu.gateways.sql.RaceSqlGateway;
import fr.pmu.repositories.RaceRepository;
import fr.pmu.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RaceConfiguration {

    private final RaceRepository raceRepository;
    private final EventBusGateway eventBusGateway;

    public RaceConfiguration(RaceRepository raceRepository, EventBusGateway eventBus) {
        this.raceRepository = raceRepository;
        this.eventBusGateway = eventBus;
    }

    @Bean
    public RaceFactory raceFactory() {
        return new RaceFactoryImpl();
    }

    @Bean
    public RaceDsGateway raceDsGateway() {
        return new RaceSqlGateway(raceRepository, eventBusGateway);
    }

    @Bean
    public StarterFactory starterFactory() {
        return new StarterFactoryImpl();
    }

    @Bean
    public CreateRacePort createRacePort() {
        return new CreateRaceUseCase(raceDsGateway(), raceFactory(), starterFactory());
    }

    @Bean
    public UpdateRacePort updateRacePort() {
        return new UpdateRaceUseCase(raceDsGateway(), raceFactory());
    }

    @Bean
    public FindRaceByUuidPort findRaceByUuidPort() {
        return new FindRaceByUuidUseCase(raceDsGateway());
    }

    @Bean
    public FindAllRacesPort findAllRacesPort() {
        return new FindAllRacesUseCase(raceDsGateway());
    }

    @Bean
    public DeleteRacePort deleteRacePort() {
        return new DeleteRaceUseCase(raceDsGateway());
    }

    @Bean
    public AddStarterToRacePort addStarterToRacePort() {
        return new AddStarterToRaceUseCase(raceDsGateway(), starterFactory());
    }

    @Bean
    public RemoveStarterFromRacePort removeStarterFromRacePort() {
        return new RemoveStarterFromRaceUseCase(raceDsGateway(), starterFactory());
    }

}
