package ru.nsu.bolotov.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.bolotov.model.dto.sport.club.SportClubInfoDto;
import ru.nsu.bolotov.model.dto.sport.club.SportCreationClubDto;
import ru.nsu.bolotov.model.dto.sport.couch.CouchInfoDto;
import ru.nsu.bolotov.model.dto.sport.sportsman.SportsmanInfoDto;
import ru.nsu.bolotov.model.dto.sport.totalinfo.TotalSportsmanInfoDto;
import ru.nsu.bolotov.model.dto.sport.type.SportTypeInfoDto;
import ru.nsu.bolotov.model.entity.sport.SportClub;
import ru.nsu.bolotov.model.entity.sport.Sportsman;
import ru.nsu.bolotov.model.entity.sport.TotalSportsmanInfo;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class TotalSportsmanInfoDtoMapper implements Function<TotalSportsmanInfo, TotalSportsmanInfoDto> {
    private final SportsmanMapper sportsmanMapper;
    private final SportTypeMapper sportTypeMapper;
    private final CouchMapper couchMapper;
    private final SportClubMapper sportClubMapper;

    @Override
    public TotalSportsmanInfoDto apply(TotalSportsmanInfo totalSportsmanInfo) {
        Sportsman sportsman = totalSportsmanInfo.getSportsman();
        SportClub sportClub = sportsman.getSportClub();
        SportClubInfoDto sportClubDto = sportClubMapper.map(sportClub);
        SportsmanInfoDto sportsmanInfoDto = sportsmanMapper.map(sportsman);
        sportsmanInfoDto.setSportClubDto(sportClubDto);
        SportTypeInfoDto sportTypeInfoDto = sportTypeMapper.map(totalSportsmanInfo.getSportType());
        CouchInfoDto couchInfoDto = couchMapper.map(totalSportsmanInfo.getCouch());
        return new TotalSportsmanInfoDto(
                totalSportsmanInfo.getTotalSportsmanInfoId(),
                sportsmanInfoDto,
                sportTypeInfoDto,
                couchInfoDto,
                totalSportsmanInfo.getSportRankLevel().name()
        );
    }
}
