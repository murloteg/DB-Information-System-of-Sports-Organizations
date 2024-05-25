package ru.nsu.bolotov.service.sport;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.bolotov.dao.sport.SportTypeRepository;
import ru.nsu.bolotov.model.dto.sport.SportTypeCreationDto;
import ru.nsu.bolotov.model.dto.sport.SportTypeInfoDto;
import ru.nsu.bolotov.model.entity.sport.SportType;
import ru.nsu.bolotov.model.exception.sport.SportTypeNotFoundException;
import ru.nsu.bolotov.model.mapper.SportTypeMapper;

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
}
