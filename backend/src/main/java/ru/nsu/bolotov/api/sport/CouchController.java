package ru.nsu.bolotov.api.sport;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nsu.bolotov.model.dto.sport.couch.CouchCreationDto;
import ru.nsu.bolotov.model.dto.sport.couch.CouchInfoDto;
import ru.nsu.bolotov.model.dto.sport.couch.CouchUpdateDto;
import ru.nsu.bolotov.service.sport.CouchService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/sport/couch/", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CouchController {
    private final CouchService couchService;

    @PostMapping
    public ResponseEntity<?> createCouch(@RequestBody @Valid CouchCreationDto couchDto,
                                         UriComponentsBuilder uriComponentsBuilder) {
        long couchId = couchService.createCouch(couchDto);
        return ResponseEntity.created(uriComponentsBuilder
                        .path("api/v1/sport/couch/{couchId}")
                        .build(Map.of("couchId", couchId)))
                .build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CouchInfoDto> getCouchInfo(@PathVariable(name = "id") long couchId) {
        CouchInfoDto couchInfo = couchService.getCouchInfo(couchId);
        return ResponseEntity.ok()
                .body(couchInfo);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteSportType(@PathVariable(name = "id") long couchId) {
        couchService.deleteCouch(couchId);
        return ResponseEntity.noContent()
                .build();
    }

    @PutMapping
    public ResponseEntity<?> updateCouch(@RequestBody @Valid CouchUpdateDto couchUpdateDto) {
        couchService.updateCouch(couchUpdateDto);
        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping(value = "/couches")
    public ResponseEntity<List<CouchInfoDto>> getCouches(@RequestParam int page, @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<CouchInfoDto> couches = couchService.getCouches(pageRequest);
        return ResponseEntity.ok()
                .body(couches);
    }
}
