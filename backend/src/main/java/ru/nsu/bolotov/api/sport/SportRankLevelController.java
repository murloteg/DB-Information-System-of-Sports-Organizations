package ru.nsu.bolotov.api.sport;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.bolotov.model.dto.sport.SportRankLevelDto;
import ru.nsu.bolotov.service.sport.SportRankLevelService;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/sport/ranks", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SportRankLevelController {
    private final SportRankLevelService sportRankLevelService;

    @GetMapping
    public ResponseEntity<List<SportRankLevelDto>> getSportRankLevels() {
        List<SportRankLevelDto> sportRankLevels = sportRankLevelService.getSportRankLevels();
        return ResponseEntity.ok()
                .body(sportRankLevels);
    }
}
