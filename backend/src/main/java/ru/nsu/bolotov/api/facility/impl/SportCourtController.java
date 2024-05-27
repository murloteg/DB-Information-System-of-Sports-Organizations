package ru.nsu.bolotov.api.facility.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nsu.bolotov.model.dto.facility.court.SportCourtCreationDto;
import ru.nsu.bolotov.model.dto.facility.court.SportCourtInfoDto;
import ru.nsu.bolotov.model.dto.facility.court.SportCourtUpdateDto;
import ru.nsu.bolotov.service.facility.impl.SportCourtService;

import java.util.List;
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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteSportCourt(@PathVariable(name = "id") long sportCourtId) {
        sportCourtService.deleteSportFacility(sportCourtId);
        return ResponseEntity.noContent()
                .build();
    }

    @PutMapping
    public ResponseEntity<?> updateSportCourt(@RequestBody @Valid SportCourtUpdateDto sportCourtUpdateDto) {
        sportCourtService.updateSportFacility(sportCourtUpdateDto);
        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping(value = "/courts")
    public ResponseEntity<List<SportCourtInfoDto>> getCourts(@RequestParam int page, @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<SportCourtInfoDto> courts = sportCourtService.getCourts(pageRequest);
        return ResponseEntity.ok()
                .body(courts);
    }
}
