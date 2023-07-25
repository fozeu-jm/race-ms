package fr.pmu.test;

import fr.pmu.boundaries.output.RaceDsGateway;
import fr.pmu.domain.entity.RaceImpl;
import fr.pmu.domain.entity.StarterImpl;
import fr.pmu.domain.factories.RaceFactory;
import fr.pmu.domain.factories.RaceFactoryImpl;
import fr.pmu.domain.factories.StarterFactory;
import fr.pmu.domain.factories.StarterFactoryImpl;
import fr.pmu.model.request.RaceUpdateModel;
import fr.pmu.model.response.RaceResponseModel;
import fr.pmu.usecases.UpdateRaceUseCase;
import fr.pmu.usecases.exception.ValidationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

public class UpdateRaceUseCaseTest {
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
    class GivenRaceRequestWithAlreadyUsedNumberOnSameDay{
        @Nested
        class WhenUpdate{
            @Test
            void thenThrowValidationException() {
                RaceUpdateModel raceUpdateModel = new RaceUpdateModel(LocalDate.now(), "RaceName", 45236);
                Mockito.when(raceDsGateway.existsByNameAndDateUuidNot(Mockito.eq(raceUpdateModel.getName()), Mockito.eq(raceUpdateModel.getDate()),Mockito.any(UUID.class)))
                        .thenReturn(false);
                Mockito.when(raceDsGateway.existsByNumberAndDateAndUuidNot(Mockito.eq(raceUpdateModel.getNumber()), Mockito.eq(raceUpdateModel.getDate()) ,Mockito.any(UUID.class)))
                        .thenReturn(true);
                Mockito.when(raceDsGateway.findByUuid(Mockito.any(UUID.class))).thenReturn(new RaceImpl(LocalDate.now(), "name", 456, Collections.singletonList(new StarterImpl("starter", 1))));
                UpdateRaceUseCase updateRaceUseCase = new UpdateRaceUseCase(raceDsGateway, raceFactory);
                Assertions.assertThatExceptionOfType(ValidationException.class)
                        .isThrownBy(() -> updateRaceUseCase.update(UUID.randomUUID(), raceUpdateModel))
                        .withMessage("Race with same name or number already exist on given date !");
            }
        }
    }

    @Nested
    class GivenRaceRequestWithAlreadyUsedNameOnSameDay{
        @Nested
        class WhenCreate{
            @Test
            void thenThrowValidationException() {
                RaceUpdateModel raceUpdateModel = new RaceUpdateModel(LocalDate.now(), "RaceName", 45236);
                Mockito.when(raceDsGateway.existsByNameAndDateUuidNot(Mockito.eq(raceUpdateModel.getName()), Mockito.eq(raceUpdateModel.getDate()),Mockito.any(UUID.class)))
                        .thenReturn(true);
                Mockito.when(raceDsGateway.existsByNumberAndDateAndUuidNot(Mockito.eq(raceUpdateModel.getNumber()), Mockito.eq(raceUpdateModel.getDate()) ,Mockito.any(UUID.class)))
                        .thenReturn(false);
                Mockito.when(raceDsGateway.findByUuid(Mockito.any(UUID.class))).thenReturn(new RaceImpl(LocalDate.now(), "name", 456, Collections.singletonList(new StarterImpl("starter", 1))));
                UpdateRaceUseCase updateRaceUseCase = new UpdateRaceUseCase(raceDsGateway, raceFactory);
                Assertions.assertThatExceptionOfType(ValidationException.class)
                        .isThrownBy(() -> updateRaceUseCase.update(UUID.randomUUID(), raceUpdateModel))
                        .withMessage("Race with same name or number already exist on given date !");
            }
        }
    }

    @Nested
    class GivenValidUpdateRaceRequest{
        @Nested
        class WhenCreate{
            @Test
            void thenReturnValidResponseModel(){
                RaceUpdateModel raceUpdateModel = new RaceUpdateModel(LocalDate.now(), "Road Rage", 45236);
                Mockito.when(raceDsGateway.existsByNameAndDateUuidNot(Mockito.eq(raceUpdateModel.getName()), Mockito.eq(raceUpdateModel.getDate()),Mockito.any(UUID.class)))
                        .thenReturn(false);
                Mockito.when(raceDsGateway.existsByNumberAndDateAndUuidNot(Mockito.eq(raceUpdateModel.getNumber()), Mockito.eq(raceUpdateModel.getDate()) ,Mockito.any(UUID.class)))
                        .thenReturn(false);
                RaceImpl race = new RaceImpl(LocalDate.now(), "name", 456, Collections.singletonList(new StarterImpl("starter", 1)));
                Mockito.when(raceDsGateway.findByUuid(Mockito.any(UUID.class))).thenReturn(race);
                UpdateRaceUseCase updateRaceUseCase = new UpdateRaceUseCase(raceDsGateway, raceFactory);
                RaceResponseModel raceResponseModel = updateRaceUseCase.update(UUID.randomUUID(), raceUpdateModel);
                Assertions.assertThat(raceResponseModel.getDate()).isEqualTo(raceUpdateModel.getDate());
                Assertions.assertThat(raceResponseModel.getName()).isEqualTo(raceUpdateModel.getName());
                Assertions.assertThat(raceResponseModel.getNumber()).isEqualTo(raceUpdateModel.getNumber());

            }
        }
    }
}
