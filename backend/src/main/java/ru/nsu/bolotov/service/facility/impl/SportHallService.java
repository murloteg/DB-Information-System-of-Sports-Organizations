package ru.nsu.bolotov.service.facility.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.bolotov.dao.facility.SportHallRepository;
import ru.nsu.bolotov.model.dto.facility.hall.SportHallCreationDto;
import ru.nsu.bolotov.model.dto.facility.hall.SportHallInfoDto;
import ru.nsu.bolotov.model.dto.facility.hall.SportHallUpdateDto;
import ru.nsu.bolotov.model.entity.facility.SportHall;
import ru.nsu.bolotov.model.enumeration.SportFacilityType;
import ru.nsu.bolotov.model.exception.facility.SportFacilityNotFoundException;
import ru.nsu.bolotov.model.mapper.facility.SportHallMapper;

import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    public void deleteSportFacility(long facilityId) {
        if (sportHallRepository.existsByFacilityId(facilityId)) {
            sportHallRepository.deleteByFacilityId(facilityId);
        } else {
            throw new SportFacilityNotFoundException("Спортивный зал не был найден");
        }
    }

    @Transactional
    public void updateSportFacility(SportHallUpdateDto sportHallUpdateDto) {
        if (sportHallRepository.existsByFacilityId(sportHallUpdateDto.getSportFacilityUpdateDto().getFacilityId())) {
            sportHallRepository.updateByFacilityId(
                    sportHallUpdateDto.getSportFacilityUpdateDto().getFacilityName(),
                    SportFacilityType.valueOf(sportHallUpdateDto.getSportFacilityUpdateDto().getFacilityType()),
                    sportHallUpdateDto.getNumberOfSeats(),
                    sportHallUpdateDto.getSportFacilityUpdateDto().getFacilityId()
            );
        } else {
            throw new SportFacilityNotFoundException("Спортивный зал не был найден");
        }
    }

    public List<SportHallInfoDto> getHalls(Pageable pageable) {
        Page<SportHall> sportHalls = sportHallRepository.findAll(pageable);
        List<SportHallInfoDto> sportHallInfoDtos = new ArrayList<>();
        for (SportHall sportHall : sportHalls) {
            sportHallInfoDtos.add(sportHallMapper.map(sportHall));
        }
        return sportHallInfoDtos;
    }
}
