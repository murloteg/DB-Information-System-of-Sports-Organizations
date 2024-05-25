package ru.nsu.bolotov.api.championship;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nsu.bolotov.model.dto.championship.ChampionshipCreationDto;
import ru.nsu.bolotov.model.dto.championship.ChampionshipExtendedInfoDto;
import ru.nsu.bolotov.service.championship.ChampionshipService;

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
}
