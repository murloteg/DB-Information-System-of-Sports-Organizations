package ru.nsu.bolotov.api.sport;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nsu.bolotov.model.dto.sport.totalinfo.TotalSportsmanInfoCreationDto;
import ru.nsu.bolotov.model.dto.sport.totalinfo.TotalSportsmanInfoDto;
import ru.nsu.bolotov.model.dto.sport.totalinfo.TotalSportsmanInfoUpdateDto;
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
}
