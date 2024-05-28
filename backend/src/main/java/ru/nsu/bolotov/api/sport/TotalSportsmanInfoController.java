package ru.nsu.bolotov.api.sport;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nsu.bolotov.model.dto.request.RequestByCouchOrRankSportsmanDto;
import ru.nsu.bolotov.model.dto.request.RequestByTypeOrRankSportsmanDto;
import ru.nsu.bolotov.model.dto.sport.couch.CouchInfoDto;
import ru.nsu.bolotov.model.dto.sport.sportsman.SportsmanManySportTypesOverviewDto;
import ru.nsu.bolotov.model.dto.sport.totalinfo.*;
import ru.nsu.bolotov.service.sport.TotalSportsmanInfoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/sport/sportsman/info", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TotalSportsmanInfoController {
    private final TotalSportsmanInfoService totalSportsmanInfoService;

    @PostMapping
    public ResponseEntity<?> createTotalSportsmanInfo(@RequestBody @Valid TotalSportsmanInfoCreationDto totalSportsmanInfoDto,
                                                      UriComponentsBuilder uriComponentsBuilder) {
        long totalSportsmanInfoId = totalSportsmanInfoService.createTotalSportsmanInfo(totalSportsmanInfoDto);
        return ResponseEntity.created(uriComponentsBuilder
                        .path("api/v1/sport/sportsman/info/{totalInfoSportsmanId}")
                        .build(Map.of("totalInfoSportsmanId", totalSportsmanInfoId)))
                .build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TotalSportsmanInfoDto> getTotalSportsmanInfo(@PathVariable(name = "id") long totalSportsmanInfoId) {
        TotalSportsmanInfoDto totalSportsmanInfoDto = totalSportsmanInfoService.getTotalSportsmanInfo(totalSportsmanInfoId);
        return ResponseEntity.ok()
                .body(totalSportsmanInfoDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTotalSportsmanInfo(@PathVariable(name = "id") long totalSportsmanInfoId) {
        totalSportsmanInfoService.deleteTotalSportsmanInfo(totalSportsmanInfoId);
        return ResponseEntity.noContent()
                .build();
    }

    @PutMapping
    public ResponseEntity<?> updateTotalSportsmanInfo(@RequestBody @Valid TotalSportsmanInfoUpdateDto totalSportsmanInfoUpdateDto) {
        totalSportsmanInfoService.updateTotalSportsmanInfo(totalSportsmanInfoUpdateDto);
        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping(value = "infos")
    public ResponseEntity<List<TotalSportsmanInfoDto>> getTotalSportsmanInfos(@RequestParam int page, @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<TotalSportsmanInfoDto> totalSportsmanInfos = totalSportsmanInfoService.getTotalSportsmanInfos(pageRequest);
        return ResponseEntity.ok()
                .body(totalSportsmanInfos);
    }

    @GetMapping(value = "/by-type-or-rank")
    public ResponseEntity<List<TotalSportsmanInfoDto>> getSportsmenBySportTypeOrSportRank(
            @RequestBody @Valid
            RequestByTypeOrRankSportsmanDto requestByTypeOrRankSportsman
    ) {
        List<TotalSportsmanInfoDto> sportsmanInfos = totalSportsmanInfoService.getSportsmanInfosBySportTypeOrSportRank(requestByTypeOrRankSportsman);
        return ResponseEntity.ok()
                .body(sportsmanInfos);
    }

    @GetMapping(value = "/by-couch-or-rank")
    public ResponseEntity<List<TotalSportsmanInfoDto>> getSportsmenByCouchOrSportRank(
            @RequestBody @Valid
            RequestByCouchOrRankSportsmanDto requestByCouchOrRankSportsman
    ) {
        List<TotalSportsmanInfoDto> sportsmanInfos = totalSportsmanInfoService.getSportsmanInfosByCouchOrSportRank(requestByCouchOrRankSportsman);
        return ResponseEntity.ok()
                .body(sportsmanInfos);
    }

    @GetMapping(value = "/many-sport-types")
    public ResponseEntity<List<SportsmanManySportTypesOverviewDto>> getSportsmenWithManySportTypes() {
        List<SportsmanManySportTypesOverviewDto> sportsmanInfos = totalSportsmanInfoService.getSportsmenWithManySportTypes();
        return ResponseEntity.ok()
                .body(sportsmanInfos);
    }

    @GetMapping(value = "/couches/by-sportsman/{id}")
    public ResponseEntity<List<CouchesOverviewDto>> getCouchesBySportsman(@PathVariable(name = "id") long sportsmanId) {
        List<CouchesOverviewDto> couchesBySportsman = totalSportsmanInfoService.getCouchesBySportsman(sportsmanId);
        return ResponseEntity.ok()
                .body(couchesBySportsman);
    }

    @GetMapping(value = "/couches/by-sport-type/{id}")
    public ResponseEntity<List<CouchInfoDto>> getCouchesBySportType(@PathVariable(name = "id") long sportTypeId) {
        List<CouchInfoDto> couches = totalSportsmanInfoService.getCouchesBySportType(sportTypeId);
        return ResponseEntity.ok()
                .body(couches);
    }
}
