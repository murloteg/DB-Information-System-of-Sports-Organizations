package ru.nsu.bolotov.api.championship;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nsu.bolotov.model.dto.championship.*;
import ru.nsu.bolotov.service.championship.ChampionshipService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/championship", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ChampionshipController {
    private final ChampionshipService championshipService;

    @PostMapping
    public ResponseEntity<?> createChampionship(@RequestBody @Valid ChampionshipCreationDto championshipCreationDto,
                                                UriComponentsBuilder uriComponentsBuilder) {
        long championshipId = championshipService.createChampionship(championshipCreationDto);
        return ResponseEntity.created(uriComponentsBuilder
                        .path("api/v1/championship/{championshipId}")
                        .build(Map.of("championshipId", championshipId)))
                .build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ChampionshipExtendedInfoDto> getChampionshipInfo(@PathVariable(name = "id") long championshipId) {
        ChampionshipExtendedInfoDto championshipInfo = championshipService.getChampionshipInfo(championshipId);
        return ResponseEntity.ok()
                .body(championshipInfo);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteChampionship(@PathVariable(name = "id") long championshipId) {
        championshipService.deleteChampionship(championshipId);
        return ResponseEntity.noContent()
                .build();
    }

    @PutMapping
    public ResponseEntity<?> updateChampionship(@RequestBody @Valid ChampionshipUpdateDto championshipUpdateDto) {
        championshipService.updateChampionship(championshipUpdateDto);
        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping(value = "/championships")
    public ResponseEntity<List<ChampionshipExtendedInfoDto>> getChampionships(@RequestParam int page, @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<ChampionshipExtendedInfoDto> championships = championshipService.getChampionships(pageRequest);
        return ResponseEntity.ok()
                .body(championships);
    }

    @PatchMapping(value = "/participants")
    public ResponseEntity<?> addParticipantsToChampionship(@RequestBody @Valid ChampionshipParticipantsUpdateDto championshipParticipantsUpdateDto) {
        championshipService.addParticipantsToChampionship(championshipParticipantsUpdateDto);
        return ResponseEntity.noContent()
                .build();
    }

    @PatchMapping(value = "/winners")
    public ResponseEntity<?> addWinnersToChampionship(@RequestBody @Valid ChampionshipWinnersUpdateDto championshipWinnersUpdateDto) {
        championshipService.addWinnersToChampionship(championshipWinnersUpdateDto);
        return ResponseEntity.noContent()
                .build();
    }
}
