package fr.pmu.test;

import fr.pmu.boundaries.output.RaceDsGateway;
import fr.pmu.domain.entity.RaceImpl;
import fr.pmu.domain.entity.StarterImpl;
import fr.pmu.domain.factories.RaceFactory;
import fr.pmu.domain.factories.RaceFactoryImpl;
import fr.pmu.domain.factories.StarterFactory;
import fr.pmu.domain.factories.StarterFactoryImpl;
import fr.pmu.model.request.StarterRequestModel;
import fr.pmu.model.response.RaceResponseModel;
import fr.pmu.model.response.StarterResponseModel;
import fr.pmu.usecases.AddStarterToRaceUseCase;
import fr.pmu.usecases.exception.ValidationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

public class AddStarterToRaceUseCaseTest {
    RaceFactory raceFactory;
    StarterFactory starterFactory;
    RaceDsGateway raceDsGateway;

    @BeforeEach
    void setup() {
        raceDsGateway = Mockito.mock(RaceDsGateway.class);
        raceFactory = new RaceFactoryImpl();
        starterFactory = new StarterFactoryImpl();
    }


    @Nested
    class GivenAddStarterRequestWithAlreadyUsedName{
        @Nested
        class WhenAdd{
            @Test
            void thenThrowValidationException(){
                StarterRequestModel starterRequestModel = new StarterRequestModel("HERO");
                RaceImpl race = new RaceImpl(LocalDate.now(), "name", 456,
                        Arrays.asList(new StarterImpl("starter", 1), new StarterImpl("HERO", 2)));
                Mockito.when(raceDsGateway.findByUuid(Mockito.any(UUID.class))).thenReturn(race);
                AddStarterToRaceUseCase addStarterToRaceUseCase = new AddStarterToRaceUseCase(raceDsGateway, starterFactory);
                Assertions.assertThatExceptionOfType(ValidationException.class)
                        .isThrownBy(() -> addStarterToRaceUseCase.addStarterToRace(UUID.randomUUID(), starterRequestModel))
                        .withMessage("Starters on the same race cannot have the same name !");
            }
        }
    }
}
