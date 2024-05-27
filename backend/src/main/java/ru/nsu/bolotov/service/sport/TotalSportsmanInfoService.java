package ru.nsu.bolotov.service.sport;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.bolotov.dao.sport.CouchRepository;
import ru.nsu.bolotov.dao.sport.SportTypeRepository;
import ru.nsu.bolotov.dao.sport.SportsmanRepository;
import ru.nsu.bolotov.dao.sport.TotalSportsmanInfoRepository;
import ru.nsu.bolotov.model.dto.sport.totalinfo.TotalSportsmanInfoCreationDto;
import ru.nsu.bolotov.model.dto.sport.totalinfo.TotalSportsmanInfoDto;
import ru.nsu.bolotov.model.dto.sport.totalinfo.TotalSportsmanInfoUpdateDto;
import ru.nsu.bolotov.model.entity.sport.*;
import ru.nsu.bolotov.model.enumeration.SportRankLevel;
import ru.nsu.bolotov.model.exception.sport.CouchNotFoundException;
import ru.nsu.bolotov.model.exception.sport.SportTypeNotFoundException;
import ru.nsu.bolotov.model.exception.sport.SportsmanNotFoundException;
import ru.nsu.bolotov.model.exception.sport.TotalSportsmanInfoNotFound;
import ru.nsu.bolotov.model.mapper.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TotalSportsmanInfoService {
    private final TotalSportsmanInfoRepository totalSportsmanInfoRepository;
    private final SportsmanRepository sportsmanRepository;
    private final SportTypeRepository sportTypeRepository;
    private final CouchRepository couchRepository;
    private final TotalSportsmanInfoDtoMapper totalSportsmanInfoDtoMapper;

    @Transactional
    public long createTotalSportsmanInfo(TotalSportsmanInfoCreationDto totalSportsmanInfoDto) {
        Sportsman sportsman = sportsmanRepository.findSportsmanBySportsmanId(totalSportsmanInfoDto.getSportsmanId())
                .orElseThrow(() -> new SportsmanNotFoundException("Спортсмен не найден"));
        SportType sportType = sportTypeRepository.findSportTypeBySportTypeId(totalSportsmanInfoDto.getSportTypeId())
                .orElseThrow(() -> new SportTypeNotFoundException("Вид спорта не найден"));
        Couch couch = couchRepository.findCouchByCouchId(totalSportsmanInfoDto.getCouchId())
                .orElseThrow(() -> new CouchNotFoundException("Тренер не найден"));
        TotalSportsmanInfo totalSportsmanInfo = new TotalSportsmanInfo(
                sportsman,
                sportType,
                couch,
                SportRankLevel.valueOf(totalSportsmanInfoDto.getSportRankLevel())
        );
        TotalSportsmanInfo savedTotalInfo = totalSportsmanInfoRepository.save(totalSportsmanInfo);
        return savedTotalInfo.getTotalSportsmanInfoId();
    }

    public TotalSportsmanInfoDto getTotalSportsmanInfo(long totalSportsmanInfoId) {
        TotalSportsmanInfo totalSportsmanInfo = totalSportsmanInfoRepository.findTotalSportsmanInfoByTotalSportsmanInfoId(totalSportsmanInfoId)
                .orElseThrow(() -> new TotalSportsmanInfoNotFound("Подробная информация о спортсмене не найдена"));
        return totalSportsmanInfoDtoMapper.apply(totalSportsmanInfo);
    }

    @Transactional
    public void deleteTotalSportsmanInfo(long totalSportsmanInfoId) {
        if (totalSportsmanInfoRepository.existsByTotalSportsmanInfoId(totalSportsmanInfoId)) {
            totalSportsmanInfoRepository.deleteByTotalSportsmanInfoId(totalSportsmanInfoId);
        } else {
            throw new TotalSportsmanInfoNotFound("Подробная информация о спортсмене не найдена");
        }
    }

    @Transactional
    public void updateTotalSportsmanInfo(TotalSportsmanInfoUpdateDto totalSportsmanInfoUpdateDto) {
        Sportsman sportsman = sportsmanRepository.findSportsmanBySportsmanId(totalSportsmanInfoUpdateDto.getSportsmanId())
                .orElseThrow(() -> new SportsmanNotFoundException("Спортсмен не найден"));
        SportType sportType = sportTypeRepository.findSportTypeBySportTypeId(totalSportsmanInfoUpdateDto.getSportTypeId())
                .orElseThrow(() -> new SportTypeNotFoundException("Вид спорта не найден"));
        Couch couch = couchRepository.findCouchByCouchId(totalSportsmanInfoUpdateDto.getCouchId())
                .orElseThrow(() -> new CouchNotFoundException("Тренер не найден"));
        if (totalSportsmanInfoRepository.existsByTotalSportsmanInfoId(totalSportsmanInfoUpdateDto.getTotalSportsmanInfoId())) {
            totalSportsmanInfoRepository.updateTotalSportsmanInfoByTotalSportsmanInfoId(
                    sportsman,
                    sportType,
                    couch,
                    SportRankLevel.valueOf(totalSportsmanInfoUpdateDto.getSportRankLevel()),
                    totalSportsmanInfoUpdateDto.getTotalSportsmanInfoId()
            );
        } else {
            throw new TotalSportsmanInfoNotFound("Подробная информация о спортсмене не найдена");
        }
    }

    public List<TotalSportsmanInfoDto> getTotalSportsmanInfos(Pageable pageable) {
        Page<TotalSportsmanInfo> totalSportsmanInfos = totalSportsmanInfoRepository.findAll(pageable);
        List<TotalSportsmanInfoDto> totalSportsmanInfoDtos = new ArrayList<>();
        for (TotalSportsmanInfo totalSportsmanInfo : totalSportsmanInfos) {
            totalSportsmanInfoDtos.add(totalSportsmanInfoDtoMapper.apply(totalSportsmanInfo));
        }
        return totalSportsmanInfoDtos;
    }
}
