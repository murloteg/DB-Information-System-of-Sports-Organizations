package ru.nsu.bolotov.service.sport;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.bolotov.dao.sport.CouchRepository;
import ru.nsu.bolotov.dao.sport.SportTypeRepository;
import ru.nsu.bolotov.dao.sport.SportsmanRepository;
import ru.nsu.bolotov.dao.sport.TotalSportsmanInfoRepository;
import ru.nsu.bolotov.model.dto.sport.*;
import ru.nsu.bolotov.model.entity.sport.*;
import ru.nsu.bolotov.model.enumeration.SportRankLevel;
import ru.nsu.bolotov.model.exception.sport.CouchNotFoundException;
import ru.nsu.bolotov.model.exception.sport.SportTypeNotFoundException;
import ru.nsu.bolotov.model.exception.sport.SportsmanNotFoundException;
import ru.nsu.bolotov.model.exception.sport.TotalSportsmanInfoNotFound;
import ru.nsu.bolotov.model.mapper.CouchMapper;
import ru.nsu.bolotov.model.mapper.SportClubMapper;
import ru.nsu.bolotov.model.mapper.SportTypeMapper;
import ru.nsu.bolotov.model.mapper.SportsmanMapper;

@Service
@RequiredArgsConstructor
public class TotalSportsmanInfoService {
    private final TotalSportsmanInfoRepository totalSportsmanInfoRepository;
    private final SportsmanRepository sportsmanRepository;
    private final SportTypeRepository sportTypeRepository;
    private final CouchRepository couchRepository;
    private final SportsmanMapper sportsmanMapper;
    private final SportTypeMapper sportTypeMapper;
    private final CouchMapper couchMapper;
    private final SportClubMapper sportClubMapper;

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
        Sportsman sportsman = totalSportsmanInfo.getSportsman();
        SportClub sportClub = sportsman.getSportClub();
        SportClubDto sportClubDto = sportClubMapper.map(sportClub);
        SportsmanInfoDto sportsmanInfoDto = sportsmanMapper.map(sportsman);
        sportsmanInfoDto.setSportClubDto(sportClubDto);
        SportTypeInfoDto sportTypeInfoDto = sportTypeMapper.map(totalSportsmanInfo.getSportType());
        CouchInfoDto couchInfoDto = couchMapper.map(totalSportsmanInfo.getCouch());
        return new TotalSportsmanInfoDto(
                sportsmanInfoDto,
                sportTypeInfoDto,
                couchInfoDto,
                totalSportsmanInfo.getSportRankLevel().name()
        );
    }
}
