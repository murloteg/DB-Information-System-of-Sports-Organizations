package ru.nsu.bolotov.api.sport;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nsu.bolotov.model.dto.sport.club.SportClubInfoDto;
import ru.nsu.bolotov.model.dto.sport.club.SportCreationClubDto;
import ru.nsu.bolotov.model.dto.sport.club.SportClubUpdateDto;
import ru.nsu.bolotov.service.sport.SportClubService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/sport/club/", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SportClubController {
    private final SportClubService sportClubService;

    @PostMapping
    public ResponseEntity<?> createSportClub(@RequestBody @Valid SportCreationClubDto sportCreationClubDto,
                                             UriComponentsBuilder uriComponentsBuilder) {
        long clubId = sportClubService.createSportClub(sportCreationClubDto);
        return ResponseEntity.created(uriComponentsBuilder
                        .path("api/v1/sport/club/{clubId}")
                        .build(Map.of("clubId", clubId)))
                .build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SportClubInfoDto> getSportClubInfo(@PathVariable(value = "id") Long clubId) {
        SportClubInfoDto sportClubInfo = sportClubService.getSportClubInfo(clubId);
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

    @GetMapping(value = "/clubs")
    public ResponseEntity<List<SportClubInfoDto>> getSportClubs(@RequestParam int page, @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<SportClubInfoDto> sportClubs = sportClubService.getSportClubs(pageRequest);
        return ResponseEntity.ok()
                .body(sportClubs);
    }
}
