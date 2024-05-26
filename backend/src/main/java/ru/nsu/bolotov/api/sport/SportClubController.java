package ru.nsu.bolotov.api.sport;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nsu.bolotov.model.dto.sport.club.SportClubDto;
import ru.nsu.bolotov.model.dto.sport.club.SportClubUpdateDto;
import ru.nsu.bolotov.service.sport.SportClubService;

import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/sport/club/", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SportClubController {
    private final SportClubService sportClubService;

    @PostMapping
    public ResponseEntity<?> createSportClub(@RequestBody @Valid SportClubDto sportClubDto,
                                             UriComponentsBuilder uriComponentsBuilder) {
        long clubId = sportClubService.createSportClub(sportClubDto);
        return ResponseEntity.created(uriComponentsBuilder
                        .path("api/v1/sport/club/{clubId}")
                        .build(Map.of("clubId", clubId)))
                .build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SportClubDto> getSportClubInfo(@PathVariable(value = "id") Long clubId) {
        SportClubDto sportClubInfo = sportClubService.getSportClubInfo(clubId);
        return ResponseEntity.ok()
                .body(sportClubInfo);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteSportClub(@PathVariable(name = "id") long clubId) {
        sportClubService.deleteSportClub(clubId);
        return ResponseEntity.noContent()
                .build();
    }

    @PutMapping
    public ResponseEntity<?> updateSportClub(@RequestBody @Valid SportClubUpdateDto sportClubUpdateDto) {
        sportClubService.updateSportClub(sportClubUpdateDto);
        return ResponseEntity.noContent()
                .build();
    }
}
