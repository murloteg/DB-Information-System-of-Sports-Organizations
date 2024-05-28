package ru.nsu.bolotov.api.sport;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nsu.bolotov.model.dto.sport.sportsman.*;
import ru.nsu.bolotov.service.sport.SportsmanService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/sport/sportsman", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SportsmanController {
    private final SportsmanService sportsmanService;

    @PostMapping
    public ResponseEntity<?> createSportsman(@RequestBody @Valid SportsmanCreationDto sportsmanDto,
                                             UriComponentsBuilder uriComponentsBuilder) {
        long sportsmanId = sportsmanService.createSportsman(sportsmanDto);
        return ResponseEntity.created(uriComponentsBuilder
                        .path("api/v1/sport/sportsman/{sportsmanId}")
                        .build(Map.of("sportsmanId", sportsmanId)))
                .build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SportsmanInfoDto> getSportsmanInfo(@PathVariable(name = "id") long sportsmanId) {
        SportsmanInfoDto sportsmanInfo = sportsmanService.getSportsmanInfo(sportsmanId);
        return ResponseEntity.ok()
                .body(sportsmanInfo);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteSportsman(@PathVariable(name = "id") long sportsmanId) {
        sportsmanService.deleteSportsman(sportsmanId);
        return ResponseEntity.noContent()
                .build();
    }

    @PutMapping
    public ResponseEntity<?> updateSportsman(@RequestBody @Valid SportsmanUpdateDto sportsmanUpdateDto) {
        sportsmanService.updateSportsman(sportsmanUpdateDto);
        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping(value = "/sportsmen")
    public ResponseEntity<List<SportsmanInfoDto>> getSportsmen(@RequestParam int page, @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<SportsmanInfoDto> sportsmen = sportsmanService.getSportsmen(pageRequest);
        return ResponseEntity.ok()
                .body(sportsmen);
    }
}
