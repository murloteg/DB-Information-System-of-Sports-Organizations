package ru.nsu.bolotov.api.championship;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nsu.bolotov.model.dto.championship.ChampionshipOrganizerCreationDto;
import ru.nsu.bolotov.model.dto.championship.ChampionshipOrganizerInfoDto;
import ru.nsu.bolotov.service.championship.ChampionshipOrganizersService;

import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/championship/organizers", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ChampionshipOrganizersController {
    private final ChampionshipOrganizersService championshipOrganizersService;

    @PostMapping
    public ResponseEntity<?> createChampionshipOrganizer(@RequestBody @Valid ChampionshipOrganizerCreationDto organizerCreationDto,
                                                         UriComponentsBuilder uriComponentsBuilder) {
        long organizerId = championshipOrganizersService.createChampionShipOrganizer(organizerCreationDto);
        return ResponseEntity.created(uriComponentsBuilder
                        .path("api/v1/championship/organizers/{organizerId}")
                        .build(Map.of("organizerId", organizerId)))
                .build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ChampionshipOrganizerInfoDto> getChampionshipOrganizerInfo(@PathVariable(name = "id") long organizerId) {
        ChampionshipOrganizerInfoDto organizerInfo = championshipOrganizersService.getChampionshipOrganizerInfo(organizerId);
        return ResponseEntity.ok()
                .body(organizerInfo);
    }
}
