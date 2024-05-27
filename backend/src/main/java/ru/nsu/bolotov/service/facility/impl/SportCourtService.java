package ru.nsu.bolotov.service.facility.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.bolotov.dao.facility.SportCourtRepository;
import ru.nsu.bolotov.model.dto.facility.court.SportCourtCreationDto;
import ru.nsu.bolotov.model.dto.facility.court.SportCourtInfoDto;
import ru.nsu.bolotov.model.dto.facility.court.SportCourtUpdateDto;
import ru.nsu.bolotov.model.entity.facility.SportCourt;
import ru.nsu.bolotov.model.enumeration.SportFacilityType;
import ru.nsu.bolotov.model.exception.facility.SportFacilityNotFoundException;
import ru.nsu.bolotov.model.mapper.facility.SportCourtMapper;

import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    public void deleteSportFacility(long facilityId) {
        if (sportCourtRepository.existsByFacilityId(facilityId)) {
            sportCourtRepository.deleteByFacilityId(facilityId);
        } else {
            throw new SportFacilityNotFoundException("Спортивный корт не был найден");
        }
    }

    @Transactional
    public void updateSportFacility(SportCourtUpdateDto sportCourtUpdateDto) {
        if (sportCourtRepository.existsByFacilityId(sportCourtUpdateDto.getSportFacilityUpdateDto().getFacilityId())) {
            sportCourtRepository.updateByFacilityId(
                    sportCourtUpdateDto.getSportFacilityUpdateDto().getFacilityName(),
                    SportFacilityType.valueOf(sportCourtUpdateDto.getSportFacilityUpdateDto().getFacilityType()),
                    sportCourtUpdateDto.getTypeOfCoverage(),
                    sportCourtUpdateDto.getSportFacilityUpdateDto().getFacilityId()
            );
        } else {
            throw new SportFacilityNotFoundException("Спортивный корт не был найден");
        }
    }

    public List<SportCourtInfoDto> getCourts(Pageable pageable) {
        Page<SportCourt> sportCourts = sportCourtRepository.findAll(pageable);
        List<SportCourtInfoDto> sportCourtInfoDtos = new ArrayList<>();
        for (SportCourt sportCourt : sportCourts) {
            sportCourtInfoDtos.add(sportCourtMapper.map(sportCourt));
        }
        return sportCourtInfoDtos;
    }
}
