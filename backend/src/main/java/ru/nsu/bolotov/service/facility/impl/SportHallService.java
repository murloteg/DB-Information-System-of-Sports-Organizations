package ru.nsu.bolotov.service.facility.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.bolotov.dao.facility.SportHallRepository;
import ru.nsu.bolotov.model.dto.facility.SportHallCreationDto;
import ru.nsu.bolotov.model.dto.facility.SportHallInfoDto;
import ru.nsu.bolotov.model.entity.facility.SportHall;
import ru.nsu.bolotov.model.exception.facility.SportFacilityNotFoundException;
import ru.nsu.bolotov.model.mapper.facility.SportHallMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class SportHallService {
    private final SportHallRepository sportHallRepository;
    private final SportHallMapper sportHallMapper;

    @Transactional
    public long createSportFacility(SportHallCreationDto facilityCreationDto) {
        log.info("Received event for sport hall with name: {}", facilityCreationDto.getSportFacilityCreationDto().getFacilityName());
        SportHall sportHall = sportHallMapper.map(facilityCreationDto);
        SportHall savedSportHall = sportHallRepository.save(sportHall);
        return savedSportHall.getFacilityId();
    }

    public SportHallInfoDto getFacilityInfo(long facilityId) {
        SportHall sportHall = sportHallRepository.findSportHallByFacilityId(facilityId)
                .orElseThrow(() -> new SportFacilityNotFoundException("Спортивный зал не был найден"));
        return sportHallMapper.map(sportHall);
    }
}
