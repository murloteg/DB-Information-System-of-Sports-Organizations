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
import ru.nsu.bolotov.model.dto.request.RequestByCouchOrRankSportsmanDto;
import ru.nsu.bolotov.model.dto.request.RequestByTypeOrRankSportsmanDto;
import ru.nsu.bolotov.model.dto.sport.couch.CouchInfoDto;
import ru.nsu.bolotov.model.dto.sport.couch.CouchOverviewDto;
import ru.nsu.bolotov.model.dto.sport.sportsman.SportsmanInfoDto;
import ru.nsu.bolotov.model.dto.sport.sportsman.SportsmanManySportTypesOverviewDto;
import ru.nsu.bolotov.model.dto.sport.totalinfo.*;
import ru.nsu.bolotov.model.dto.sport.type.SportTypeInfoDto;
import ru.nsu.bolotov.model.entity.sport.*;
import ru.nsu.bolotov.model.enumeration.SportRankLevel;
import ru.nsu.bolotov.model.exception.sport.CouchNotFoundException;
import ru.nsu.bolotov.model.exception.sport.SportTypeNotFoundException;
import ru.nsu.bolotov.model.exception.sport.SportsmanNotFoundException;
import ru.nsu.bolotov.model.exception.sport.TotalSportsmanInfoNotFound;
import ru.nsu.bolotov.model.mapper.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TotalSportsmanInfoService {
    private final TotalSportsmanInfoRepository totalSportsmanInfoRepository;
    private final SportsmanRepository sportsmanRepository;
    private final SportTypeRepository sportTypeRepository;
    private final CouchRepository couchRepository;
    private final TotalSportsmanInfoDtoMapper totalSportsmanInfoDtoMapper;
    private final SportsmanManySportTypesDtoMapper sportsmanManySportTypesDtoMapper;
    private final CouchOverviewDtoMapper couchOverviewDtoMapper;
    private final CouchMapper couchMapper;

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

    public List<TotalSportsmanInfoDto> getSportsmanInfosBySportTypeOrSportRank(RequestByTypeOrRankSportsmanDto requestByTypeOrRankSportsman) {
        SportType sportType = sportTypeRepository.findSportTypeBySportTypeId(requestByTypeOrRankSportsman.getSportTypeId())
                .orElseThrow(() -> new SportTypeNotFoundException("Вид спорта не был найден"));
        List<TotalSportsmanInfo> sportsmanInfosBySportType = totalSportsmanInfoRepository.getAllBySportType(sportType);
        List<TotalSportsmanInfoDto> totalSportsmanInfoDtos = new ArrayList<>();
        for (TotalSportsmanInfo totalSportsmanInfo : sportsmanInfosBySportType) {
            totalSportsmanInfoDtos.add(totalSportsmanInfoDtoMapper.apply(totalSportsmanInfo));
        }

        List<SportRankLevel> greaterOrEqualsRankLevels = findAllGreaterOrEqualsRankLevels(requestByTypeOrRankSportsman.getSportRankLevel());
        for (SportRankLevel sportRankLevel : greaterOrEqualsRankLevels) {
            List<TotalSportsmanInfo> sportsmanInfosBySportRankLevel = totalSportsmanInfoRepository.getAllBySportRankLevel(sportRankLevel);
            for (TotalSportsmanInfo totalSportsmanInfo : sportsmanInfosBySportRankLevel) {
                totalSportsmanInfoDtos.add(totalSportsmanInfoDtoMapper.apply(totalSportsmanInfo));
            }
        }
        return totalSportsmanInfoDtos;
    }

    public List<TotalSportsmanInfoDto> getSportsmanInfosByCouchOrSportRank(RequestByCouchOrRankSportsmanDto requestByCouchOrRankSportsman) {
        Couch couch = couchRepository.findCouchByCouchId(requestByCouchOrRankSportsman.getCouchId())
                .orElseThrow(() -> new CouchNotFoundException("Тренер не найден"));
        List<TotalSportsmanInfo> sportsmanInfosByCouch = totalSportsmanInfoRepository.getAllByCouch(couch);
        List<TotalSportsmanInfoDto> totalSportsmanInfoDtos = new ArrayList<>();
        for (TotalSportsmanInfo totalSportsmanInfo : sportsmanInfosByCouch) {
            totalSportsmanInfoDtos.add(totalSportsmanInfoDtoMapper.apply(totalSportsmanInfo));
        }

        List<SportRankLevel> greaterOrEqualsRankLevels = findAllGreaterOrEqualsRankLevels(requestByCouchOrRankSportsman.getSportRankLevel());
        for (SportRankLevel sportRankLevel : greaterOrEqualsRankLevels) {
            List<TotalSportsmanInfo> sportsmanInfosBySportRankLevel = totalSportsmanInfoRepository.getAllBySportRankLevel(sportRankLevel);
            for (TotalSportsmanInfo totalSportsmanInfo : sportsmanInfosBySportRankLevel) {
                totalSportsmanInfoDtos.add(totalSportsmanInfoDtoMapper.apply(totalSportsmanInfo));
            }
        }
        return totalSportsmanInfoDtos;
    }

    public List<SportsmanManySportTypesOverviewDto> getSportsmenWithManySportTypes() {
        List<Sportsman> sportsmen = totalSportsmanInfoRepository.getSportsmenWithManySportTypes();
        List<TotalSportsmanInfo> totalSportsmanInfos = new ArrayList<>();
        for (Sportsman sportsman : sportsmen) {
            totalSportsmanInfos.addAll(totalSportsmanInfoRepository.findTotalSportsmanInfoBySportsman(sportsman));
        }
        List<SportsmanManySportTypesOverviewDto> sportsmanInfoDtos = new ArrayList<>();
        for (TotalSportsmanInfo totalSportsmanInfo : totalSportsmanInfos) {
            TotalSportsmanInfoDto totalSportsmanInfoDto = totalSportsmanInfoDtoMapper.apply(totalSportsmanInfo);
            SportsmanInfoDto sportsmanInfoDto = totalSportsmanInfoDto.getSportsmanInfoDto();
            if (sportsmanPresentsInSportsmenManySportTypesOverviewDtoList(sportsmanInfoDto, sportsmanInfoDtos)) {
                SportsmanManySportTypesOverviewDto foundOverviewDto = findSportsmanInSportsmenManySportTypesOverviewDtoListById(
                        sportsmanInfoDto.getSportsmanId(),
                        sportsmanInfoDtos
                );
                List<SportTypeInfoDto> sportTypeInfoDtos = foundOverviewDto.getSportTypeInfoDto();
                List<SportTypeInfoDto> updatedSportTypes = new ArrayList<>(sportTypeInfoDtos);
                updatedSportTypes.add(totalSportsmanInfoDto.getSportTypeInfoDto());
                foundOverviewDto.setSportTypeInfoDto(updatedSportTypes);
            } else {
                sportsmanInfoDtos.add(sportsmanManySportTypesDtoMapper.apply(totalSportsmanInfo));
            }
        }
        return sportsmanInfoDtos;
    }

    public List<CouchesOverviewDto> getCouchesBySportsman(long sportsmanId) {
        Sportsman sportsman = sportsmanRepository.findSportsmanBySportsmanId(sportsmanId)
                .orElseThrow(() -> new SportsmanNotFoundException("Спортсмен не найден"));
        List<TotalSportsmanInfo> totalSportsmanInfos = totalSportsmanInfoRepository.getAllBySportsman(sportsman);
        List<CouchesOverviewDto> couchesOverviewDtos = new ArrayList<>();
        for (TotalSportsmanInfo totalSportsmanInfo : totalSportsmanInfos) {
            TotalSportsmanInfoDto totalSportsmanInfoDto = totalSportsmanInfoDtoMapper.apply(totalSportsmanInfo);
            SportsmanInfoDto sportsmanInfoDto = totalSportsmanInfoDto.getSportsmanInfoDto();
            CouchOverviewDto couchOverviewDto = couchOverviewDtoMapper.apply(totalSportsmanInfo);
            if (sportsmanPresentsInCouchesOverviewDtoList(sportsmanInfoDto, couchesOverviewDtos)) {
                CouchesOverviewDto foundOverviewDto = findSportsmanInCouchesOverviewDtoListById(sportsmanId, couchesOverviewDtos);
                List<CouchOverviewDto> couchInfoDtos = foundOverviewDto.getCouchInfoDto();
                List<CouchOverviewDto> updatedCouchInfoDtos = new ArrayList<>(couchInfoDtos);
                updatedCouchInfoDtos.add(couchOverviewDto);
                foundOverviewDto.setCouchInfoDto(updatedCouchInfoDtos);
            } else {
                CouchesOverviewDto couchesOverviewDto = new CouchesOverviewDto(
                        totalSportsmanInfoDto.getSportsmanInfoDto(),
                        List.of(couchOverviewDto)
                );
                couchesOverviewDtos.add(couchesOverviewDto);
            }
        }
        return couchesOverviewDtos;
    }

    public List<CouchInfoDto> getCouchesBySportType(long sportTypeId) {
        SportType sportType = sportTypeRepository.findSportTypeBySportTypeId(sportTypeId)
                .orElseThrow(() -> new SportTypeNotFoundException("Вид спорта не был найден"));
        List<Couch> couches = totalSportsmanInfoRepository.getAllCouchesBySportType(sportType);
        List<CouchInfoDto> couchInfoDtos = new ArrayList<>();
        for (Couch couch : couches) {
            couchInfoDtos.add(couchMapper.map(couch));
        }
        return couchInfoDtos;
    }

    private List<SportRankLevel> findAllGreaterOrEqualsRankLevels(SportRankLevel sportRankLevel) {
        SportRankLevel[] values = SportRankLevel.values();
        return Arrays.stream(values).filter(value -> value.getLevel() >= sportRankLevel.getLevel()).toList();
    }

    private boolean sportsmanPresentsInSportsmenManySportTypesOverviewDtoList(
            SportsmanInfoDto sportsman,
            List<SportsmanManySportTypesOverviewDto> sportsmanInfoDtos
    ) {
        return sportsmanInfoDtos.stream()
                .anyMatch(sportsmanManySportTypesOverviewDto ->
                        sportsmanManySportTypesOverviewDto.getSportsmanInfoDto().getSportsmanId() == sportsman.getSportsmanId()
                );
    }

    private boolean sportsmanPresentsInCouchesOverviewDtoList(
            SportsmanInfoDto sportsman,
            List<CouchesOverviewDto> couchesOverviewDtos
    ) {
        return couchesOverviewDtos.stream()
                .anyMatch(couchesOverviewDto ->
                        couchesOverviewDto.getSportsmanInfoDto().getSportsmanId() == sportsman.getSportsmanId()
                );
    }

    private SportsmanManySportTypesOverviewDto findSportsmanInSportsmenManySportTypesOverviewDtoListById(
            long sportsmanId,
            List<SportsmanManySportTypesOverviewDto> sportsmanInfoDtos
    ) {
        return sportsmanInfoDtos.stream()
                .filter(sportsmanInfoDto -> sportsmanInfoDto.getSportsmanInfoDto().getSportsmanId() == sportsmanId)
                .findFirst().orElseThrow(() -> new IllegalStateException("Спортсмен не найден в результирующем списке"));
    }

    private CouchesOverviewDto findSportsmanInCouchesOverviewDtoListById(
            long sportsmanId,
            List<CouchesOverviewDto> couchesOverviewDtos
    ) {
        return couchesOverviewDtos.stream()
                .filter(couchesOverviewDto -> couchesOverviewDto.getSportsmanInfoDto().getSportsmanId() == sportsmanId)
                .findFirst().orElseThrow(() -> new IllegalStateException("Спортсмен не найден в результирующем списке"));
    }
}
