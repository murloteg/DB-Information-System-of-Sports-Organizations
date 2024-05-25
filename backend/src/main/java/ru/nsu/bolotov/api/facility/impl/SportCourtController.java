package ru.nsu.bolotov.api.facility.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nsu.bolotov.model.dto.facility.SportCourtCreationDto;
import ru.nsu.bolotov.model.dto.facility.SportCourtInfoDto;
import ru.nsu.bolotov.service.facility.impl.SportCourtService;

import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/facility/court")
@RequiredArgsConstructor
public class SportCourtController {
    private final SportCourtService sportCourtService;

    @PostMapping
    public ResponseEntity<?> createSportFacility(@RequestBody @Valid SportCourtCreationDto facilityCreationDto,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        long sportFacilityId = sportCourtService.createSportFacility(facilityCreationDto);
        return ResponseEntity.created(uriComponentsBuilder
                        .path("/api/v1/facility/court/{facilityId}")
                        .build(Map.of("facilityId", sportFacilityId)))
                .build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SportCourtInfoDto> getSportFacility(@PathVariable(name = "id") long facilityId) {
        SportCourtInfoDto facilityInfo = sportCourtService.getFacilityInfo(facilityId);
        return ResponseEntity.ok()
                .body(facilityInfo);
    }
}
