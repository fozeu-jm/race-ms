package fr.pmu.controllers;

import fr.pmu.boundaries.input.*;
import fr.pmu.controllers.dto.RaceRequestDTO;
import fr.pmu.controllers.dto.RaceUpdateDTO;
import fr.pmu.model.request.RaceRequestModel;
import fr.pmu.model.request.RaceUpdateModel;
import fr.pmu.model.request.StarterRequestModel;
import fr.pmu.model.response.RaceResponseModel;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/race")
public class RaceController {
    final CreateRacePort createRacePort;
    final UpdateRacePort updateRacePort;
    private final FindRaceByUuidPort findRaceByUuidPort;
    private final FindAllRacesPort findAllRacesPort;
    private final DeleteRacePort deleteRacePort;
    private final AddStarterToRacePort addStarterToRacePort;
    private final RemoveStarterFromRacePort removeStarterFromRacePort;


    public static final String UUID_REGEX = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";

    public RaceController(CreateRacePort createRacePort, UpdateRacePort updateRacePort, FindRaceByUuidPort findRaceByUuidPort, FindAllRacesPort findAllRacesPort, DeleteRacePort deleteRacePort, AddStarterToRacePort addStarterToRacePort, RemoveStarterFromRacePort removeStarterFromRacePort) {
        this.createRacePort = createRacePort;
        this.updateRacePort = updateRacePort;
        this.findRaceByUuidPort = findRaceByUuidPort;
        this.findAllRacesPort = findAllRacesPort;
        this.deleteRacePort = deleteRacePort;
        this.addStarterToRacePort = addStarterToRacePort;
        this.removeStarterFromRacePort = removeStarterFromRacePort;
    }

    @PostMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RaceResponseModel> create(@RequestBody @Valid RaceRequestDTO requestDTO) {
        return new ResponseEntity<>(createRacePort.create(toRequestModel(requestDTO)), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{raceUuid:" + UUID_REGEX + "}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RaceResponseModel> findByUuid(@PathVariable String raceUuid) {
        return new ResponseEntity<>(findRaceByUuidPort.findByUuid(UUID.fromString(raceUuid)), HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<RaceResponseModel>> findAll() {
        return new ResponseEntity<>(findAllRacesPort.findAll(), HttpStatus.OK);
    }

    @PutMapping(value = "/{raceUuid:" + UUID_REGEX + "}", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RaceResponseModel> update(@RequestBody @Valid RaceUpdateDTO requestDTO, @PathVariable String raceUuid) {
        return new ResponseEntity<>(updateRacePort.update(UUID.fromString(raceUuid), toRequestModel(requestDTO)), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{raceUuid:" + UUID_REGEX + "}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Boolean> delete(@PathVariable String raceUuid) {
        return new ResponseEntity<>(deleteRacePort.delete(UUID.fromString(raceUuid)), HttpStatus.OK);
    }

    @PostMapping(value = "/{raceUuid:" + UUID_REGEX + "}/starter/{starterName}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RaceResponseModel> addStarterToRace(@PathVariable String raceUuid, @PathVariable String starterName) {
        return new ResponseEntity<>(addStarterToRacePort.addStarterToRace(UUID.fromString(raceUuid),
                new StarterRequestModel(starterName)), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{raceUuid:" + UUID_REGEX + "}/starter/{starterName}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RaceResponseModel> removeStarterFromRace(@PathVariable String raceUuid, @PathVariable String starterName) {
        return new ResponseEntity<>(removeStarterFromRacePort.removeStarterFromRacePort(UUID.fromString(raceUuid),
                new StarterRequestModel(starterName)), HttpStatus.OK);
    }

    private RaceRequestModel toRequestModel(RaceRequestDTO requestDTO) {
        RaceRequestModel raceRequestModel = new RaceRequestModel();
        raceRequestModel.setDate(requestDTO.getDate());
        raceRequestModel.setName(requestDTO.getName());
        raceRequestModel.setNumber(requestDTO.getNumber());
        raceRequestModel.setStarters(requestDTO.getStarters().stream().map(s -> new StarterRequestModel(s.getName())).toList());
        return raceRequestModel;
    }

    private RaceUpdateModel toRequestModel(RaceUpdateDTO requestDTO) {
        RaceUpdateModel raceUpdateModel = new RaceUpdateModel();
        raceUpdateModel.setDate(requestDTO.getDate());
        raceUpdateModel.setName(requestDTO.getName());
        raceUpdateModel.setNumber(requestDTO.getNumber());
        return raceUpdateModel;
    }
}
