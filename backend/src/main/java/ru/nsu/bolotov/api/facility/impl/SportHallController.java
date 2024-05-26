package ru.nsu.bolotov.api.facility.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nsu.bolotov.model.dto.facility.hall.SportHallCreationDto;
import ru.nsu.bolotov.model.dto.facility.hall.SportHallInfoDto;
import ru.nsu.bolotov.model.dto.facility.hall.SportHallUpdateDto;
import ru.nsu.bolotov.service.facility.impl.SportHallService;

import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/facility/hall")
@RequiredArgsConstructor
public class SportHallController {
    private final SportHallService sportHallService;

    @PostMapping
    public ResponseEntity<?> createSportFacility(@RequestBody @Valid SportHallCreationDto facilityCreationDto,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        long sportFacilityId = sportHallService.createSportFacility(facilityCreationDto);
        return ResponseEntity.created(uriComponentsBuilder
                        .path("/api/v1/facility/hall/{facilityId}")
                        .build(Map.of("facilityId", sportFacilityId)))
                .build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SportHallInfoDto> getSportFacility(@PathVariable(name = "id") long facilityId) {
        SportHallInfoDto facilityInfo = sportHallService.getFacilityInfo(facilityId);
        return ResponseEntity.ok()
                .body(facilityInfo);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteSportHall(@PathVariable(name = "id") long sportHallId) {
        sportHallService.deleteSportFacility(sportHallId);
        return ResponseEntity.noContent()
                .build();
    }

    @PutMapping
    public ResponseEntity<?> updateSportHall(@RequestBody @Valid SportHallUpdateDto sportHallUpdateDto) {
        sportHallService.updateSportFacility(sportHallUpdateDto);
        return ResponseEntity.noContent()
                .build();
    }
}
