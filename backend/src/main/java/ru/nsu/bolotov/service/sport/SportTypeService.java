package ru.nsu.bolotov.service.sport;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.bolotov.dao.sport.SportTypeRepository;
import ru.nsu.bolotov.model.dto.sport.type.SportTypeCreationDto;
import ru.nsu.bolotov.model.dto.sport.type.SportTypeInfoDto;
import ru.nsu.bolotov.model.dto.sport.type.SportTypeUpdateDto;
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
}
