package fr.pmu.test;

import fr.pmu.boundaries.output.RaceDsGateway;
import fr.pmu.domain.factories.RaceFactory;
import fr.pmu.domain.factories.RaceFactoryImpl;
import fr.pmu.domain.factories.StarterFactory;
import fr.pmu.domain.factories.StarterFactoryImpl;
import fr.pmu.model.request.RaceRequestModel;
import fr.pmu.model.request.StarterRequestModel;
import fr.pmu.model.response.RaceResponseModel;
import fr.pmu.model.response.StarterResponseModel;
import fr.pmu.usecases.CreateRaceUseCase;
import fr.pmu.usecases.exception.ValidationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class CreateRaceUseCaseTest {
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
    class GivenRaceRequestWithLessThan3Starters {

        @Nested
        class WhenCreate {
            @Test
            void thenThrowValidationException() {
                RaceRequestModel requestModel = new RaceRequestModel(LocalDate.now(), "RaceName", 45236, Collections.singletonList(new StarterRequestModel("GREEN BROCCOLI")));
                Mockito.when(raceDsGateway.existsByNameAndDateUuidNot(Mockito.eq(requestModel.getName()), Mockito.eq(requestModel.getDate()),Mockito.any(UUID.class)))
                        .thenReturn(false);
                Mockito.when(raceDsGateway.existsByNumberAndDateAndUuidNot(Mockito.eq(requestModel.getNumber()), Mockito.eq(requestModel.getDate()) ,Mockito.any(UUID.class)))
                        .thenReturn(false);
                CreateRaceUseCase createRaceUseCase = new CreateRaceUseCase(raceDsGateway, raceFactory, starterFactory);
                Assertions.assertThatExceptionOfType(ValidationException.class)
                        .isThrownBy(() -> createRaceUseCase.create(requestModel))
                        .withMessage("Race must have at least 3 starters");
            }
        }
    }

    @Nested
    class GivenRaceRequestWithAlreadyUsedNumberOnSameDay{
        @Nested
        class WhenCreate{
            @Test
            void thenThrowValidationException() {
                RaceRequestModel requestModel = new RaceRequestModel(LocalDate.now(), "RaceName", 45236,
                        Arrays.asList(new StarterRequestModel("READY RIDE"), new StarterRequestModel("GREEn BROCCOLI"), new StarterRequestModel("RED RIOT")));
                Mockito.when(raceDsGateway.existsByNameAndDateUuidNot(Mockito.eq(requestModel.getName()), Mockito.eq(requestModel.getDate()),Mockito.any(UUID.class)))
                        .thenReturn(false);
                Mockito.when(raceDsGateway.existsByNumberAndDateAndUuidNot(Mockito.eq(requestModel.getNumber()), Mockito.eq(requestModel.getDate()) ,Mockito.any(UUID.class)))
                        .thenReturn(true);
                CreateRaceUseCase createRaceUseCase = new CreateRaceUseCase(raceDsGateway, raceFactory, starterFactory);
                Assertions.assertThatExceptionOfType(ValidationException.class)
                        .isThrownBy(() -> createRaceUseCase.create(requestModel))
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
                RaceRequestModel requestModel = new RaceRequestModel(LocalDate.now(), "RaceName", 45236,
                        Arrays.asList(new StarterRequestModel("READY RIDE"), new StarterRequestModel("GREEn BROCCOLI"), new StarterRequestModel("RED RIOT")));
                Mockito.when(raceDsGateway.existsByNameAndDateUuidNot(Mockito.eq(requestModel.getName()), Mockito.eq(requestModel.getDate()),Mockito.any(UUID.class)))
                        .thenReturn(true);
                Mockito.when(raceDsGateway.existsByNumberAndDateAndUuidNot(Mockito.eq(requestModel.getNumber()), Mockito.eq(requestModel.getDate()) ,Mockito.any(UUID.class)))
                        .thenReturn(false);
                CreateRaceUseCase createRaceUseCase = new CreateRaceUseCase(raceDsGateway, raceFactory, starterFactory);
                Assertions.assertThatExceptionOfType(ValidationException.class)
                        .isThrownBy(() -> createRaceUseCase.create(requestModel))
                        .withMessage("Race with same name or number already exist on given date !");
            }
        }
    }

    @Nested
    class GivenRaceRequestWithStartersWithSameName{
        @Nested
        class WhenCreate{
            @Test
            void thenThrowValidationException() {
                RaceRequestModel requestModel = new RaceRequestModel(LocalDate.now(), "RaceName", 45236,
                        Arrays.asList(new StarterRequestModel("READY RIDE"), new StarterRequestModel("RED RIOT"), new StarterRequestModel("RED RIOT")));
                Mockito.when(raceDsGateway.existsByNameAndDateUuidNot(Mockito.eq(requestModel.getName()), Mockito.eq(requestModel.getDate()),Mockito.any(UUID.class)))
                        .thenReturn(false);
                Mockito.when(raceDsGateway.existsByNumberAndDateAndUuidNot(Mockito.eq(requestModel.getNumber()), Mockito.eq(requestModel.getDate()) ,Mockito.any(UUID.class)))
                        .thenReturn(false);
                CreateRaceUseCase createRaceUseCase = new CreateRaceUseCase(raceDsGateway, raceFactory, starterFactory);
                Assertions.assertThatExceptionOfType(ValidationException.class)
                        .isThrownBy(() -> createRaceUseCase.create(requestModel))
                        .withMessage("Starters on the same race cannot have the same name !");
            }
        }
    }

    @Nested
    class GivenValidRaceRequest{
        @Nested
        class WhenCreate{
            @Test
            void thenReturnValidResponseModel(){
                RaceRequestModel requestModel = new RaceRequestModel(LocalDate.now(), "RaceName", 45236,
                        Arrays.asList(new StarterRequestModel("READY RIDE"), new StarterRequestModel("GREEN BROCCOLI"), new StarterRequestModel("RED RIOT")));
                Mockito.when(raceDsGateway.existsByNameAndDateUuidNot(Mockito.eq(requestModel.getName()), Mockito.eq(requestModel.getDate()),Mockito.any(UUID.class)))
                        .thenReturn(false);
                Mockito.when(raceDsGateway.existsByNumberAndDateAndUuidNot(Mockito.eq(requestModel.getNumber()), Mockito.eq(requestModel.getDate()) ,Mockito.any(UUID.class)))
                        .thenReturn(false);
                CreateRaceUseCase createRaceUseCase = new CreateRaceUseCase(raceDsGateway, raceFactory, starterFactory);
                RaceResponseModel raceResponseModel = createRaceUseCase.create(requestModel);

                Assertions.assertThat(raceResponseModel.getDate()).isEqualTo(requestModel.getDate());
                Assertions.assertThat(raceResponseModel.getName()).isEqualTo(requestModel.getName());
                Assertions.assertThat(raceResponseModel.getNumber()).isEqualTo(requestModel.getNumber());
                Assertions.assertThat(raceResponseModel.getStarters()).hasSize(3);
                requestModel.getStarters().forEach(starters -> {
                    Assertions.assertThat(raceResponseModel.getStarters().stream().map(StarterResponseModel::getName).toList())
                            .contains(starters.getName());
                });

                for(int i = 0; i < raceResponseModel.getStarters().size(); i++){
                    Assertions.assertThat(raceResponseModel.getStarters().get(i).getNumber()).isEqualTo(i + 1);
                }

            }
        }
    }
}
