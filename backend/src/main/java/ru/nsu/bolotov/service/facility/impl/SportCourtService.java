package ru.nsu.bolotov.service.facility.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.bolotov.dao.facility.SportCourtRepository;
import ru.nsu.bolotov.model.dto.facility.SportCourtCreationDto;
import ru.nsu.bolotov.model.dto.facility.SportCourtInfoDto;
import ru.nsu.bolotov.model.entity.facility.SportCourt;
import ru.nsu.bolotov.model.exception.facility.SportFacilityNotFoundException;
import ru.nsu.bolotov.model.mapper.facility.SportCourtMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class SportCourtService {
    private final SportCourtMapper sportCourtMapper;
    private final SportCourtRepository sportCourtRepository;

    @Transactional
    public long createSportFacility(SportCourtCreationDto facilityCreationDto) {
        log.info("Received event for sport court with name: {}", facilityCreationDto.getSportFacilityCreationDto().getFacilityName());
        SportCourt sportCourt = sportCourtMapper.map(facilityCreationDto);
        SportCourt savedSportCourt = sportCourtRepository.save(sportCourt);
        return savedSportCourt.getFacilityId();
    }

    public SportCourtInfoDto getFacilityInfo(long facilityId) {
        SportCourt sportCourt = sportCourtRepository.findSportCourtByFacilityId(facilityId)
                .orElseThrow(() -> new SportFacilityNotFoundException("Спортивный корт не был найден"));
        return sportCourtMapper.map(sportCourt);
    }
}
