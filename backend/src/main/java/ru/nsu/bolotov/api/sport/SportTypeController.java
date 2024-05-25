package ru.nsu.bolotov.api.sport;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nsu.bolotov.model.dto.sport.SportTypeCreationDto;
import ru.nsu.bolotov.model.dto.sport.SportTypeInfoDto;
import ru.nsu.bolotov.service.sport.SportTypeService;

import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/sport/type", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SportTypeController {
    private final SportTypeService sportTypeService;

    @PostMapping
    public ResponseEntity<?> createSportType(@RequestBody @Valid SportTypeCreationDto sportTypeDto,
                                             UriComponentsBuilder uriComponentsBuilder) {
        long sportTypeId = sportTypeService.createSportType(sportTypeDto);
        return ResponseEntity.created(uriComponentsBuilder
                        .path("api/v1/sport/type/{typeId}")
                        .build(Map.of("typeId", sportTypeId)))
                .build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SportTypeInfoDto> getSportTypeInfo(@PathVariable(name = "id") long sportTypeId) {
        SportTypeInfoDto sportTypeInfo = sportTypeService.getSportTypeInfo(sportTypeId);
        return ResponseEntity.ok()
                .body(sportTypeInfo);
    }
}
