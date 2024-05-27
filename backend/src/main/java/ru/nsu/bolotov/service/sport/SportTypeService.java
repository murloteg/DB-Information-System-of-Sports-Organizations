package ru.nsu.bolotov.service.sport;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.bolotov.dao.sport.SportTypeRepository;
import ru.nsu.bolotov.model.dto.sport.type.SportTypeCreationDto;
import ru.nsu.bolotov.model.dto.sport.type.SportTypeInfoDto;
import ru.nsu.bolotov.model.dto.sport.type.SportTypeUpdateDto;
import ru.nsu.bolotov.model.entity.sport.SportType;
import ru.nsu.bolotov.model.exception.sport.SportTypeNotFoundException;
import ru.nsu.bolotov.model.mapper.SportTypeMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SportTypeService {
    private final SportTypeRepository sportTypeRepository;
    private final SportTypeMapper sportTypeMapper;

    @Transactional
    public long createSportType(SportTypeCreationDto sportTypeDto) {
        SportType sportType = sportTypeMapper.map(sportTypeDto);
        SportType savedSportType = sportTypeRepository.save(sportType);
        return savedSportType.getSportTypeId();
    }

    public SportTypeInfoDto getSportTypeInfo(long sportTypeId) {
        SportType sportType = sportTypeRepository.findSportTypeBySportTypeId(sportTypeId)
                .orElseThrow(() -> new SportTypeNotFoundException("Вид спорта не найден"));
        return sportTypeMapper.map(sportType);
    }

    @Transactional
    public void deleteSportType(long sportTypeId) {
        if (sportTypeRepository.existsBySportTypeId(sportTypeId)) {
            sportTypeRepository.deleteBySportTypeId(sportTypeId);
        } else {
            throw new SportTypeNotFoundException("Вид спорта не найден");
        }
    }

    @Transactional
    public void updateSportType(SportTypeUpdateDto sportTypeUpdateDto) {
        if (sportTypeRepository.existsBySportTypeId(sportTypeUpdateDto.getSportTypeId())) {
            sportTypeRepository.updateSportTypeBySportTypeId(
                    sportTypeUpdateDto.getSportName(),
                    sportTypeUpdateDto.getSportDescription(),
                    sportTypeUpdateDto.getSportTypeId()
            );
        } else {
            throw new SportTypeNotFoundException("Вид спорта не найден");
        }
    }

    public List<SportTypeInfoDto> getSportTypes(Pageable pageable) {
        Page<SportType> sportTypes = sportTypeRepository.findAll(pageable);
        List<SportTypeInfoDto> sportTypeInfoDtos = new ArrayList<>();
        for (SportType sportType : sportTypes) {
            sportTypeInfoDtos.add(sportTypeMapper.map(sportType));
        }
        return sportTypeInfoDtos;
    }
}
