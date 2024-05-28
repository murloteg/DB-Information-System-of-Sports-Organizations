package ru.nsu.bolotov.api.championship;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nsu.bolotov.model.dto.championship.organizer.ChampionshipOrganizerCreationDto;
import ru.nsu.bolotov.model.dto.championship.organizer.ChampionshipOrganizerInfoDto;
import ru.nsu.bolotov.model.dto.championship.organizer.ChampionshipOrganizerStatisticInfoDto;
import ru.nsu.bolotov.model.dto.championship.organizer.ChampionshipOrganizerUpdateDto;
import ru.nsu.bolotov.model.dto.request.RequestByPeriodDto;
import ru.nsu.bolotov.service.championship.ChampionshipOrganizersService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/championship/organizer", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ChampionshipOrganizersController {
    private final ChampionshipOrganizersService championshipOrganizersService;

    @PostMapping
    public ResponseEntity<?> createChampionshipOrganizer(@RequestBody @Valid ChampionshipOrganizerCreationDto organizerCreationDto,
                                                         UriComponentsBuilder uriComponentsBuilder) {
        long organizerId = championshipOrganizersService.createChampionShipOrganizer(organizerCreationDto);
        return ResponseEntity.created(uriComponentsBuilder
                        .path("api/v1/championship/organizer/{organizerId}")
                        .build(Map.of("organizerId", organizerId)))
                .build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ChampionshipOrganizerInfoDto> getChampionshipOrganizerInfo(@PathVariable(name = "id") long organizerId) {
        ChampionshipOrganizerInfoDto organizerInfo = championshipOrganizersService.getChampionshipOrganizerInfo(organizerId);
        return ResponseEntity.ok()
                .body(organizerInfo);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteChampionshipOrganizer(@PathVariable(name = "id") long organizerId) {
        championshipOrganizersService.deleteChampionshipOrganizer(organizerId);
        return ResponseEntity.noContent()
                .build();
    }

    @PutMapping
    public ResponseEntity<?> updateChampionshipOrganizer(@RequestBody @Valid ChampionshipOrganizerUpdateDto championshipOrganizerUpdateDto) {
        championshipOrganizersService.updateChampionshipOrganizer(championshipOrganizerUpdateDto);
        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping(value = "/organizers")
    public ResponseEntity<List<ChampionshipOrganizerInfoDto>> getOrganizers(@RequestParam int page, @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<ChampionshipOrganizerInfoDto> organizers = championshipOrganizersService.getOrganizers(pageRequest);
        return ResponseEntity.ok()
                .body(organizers);
    }

    @GetMapping(value = "/organizers/statistic-by-period")
    public ResponseEntity<List<ChampionshipOrganizerStatisticInfoDto>> getOrganizersStatisticsByPeriod(
            @RequestBody @Valid RequestByPeriodDto requestByPeriodDto
    ) {
        List<ChampionshipOrganizerStatisticInfoDto> organizersStatistics = championshipOrganizersService.getOrganizersStatisticsByPeriod(requestByPeriodDto);
        return ResponseEntity.ok()
                .body(organizersStatistics);
    }
}
