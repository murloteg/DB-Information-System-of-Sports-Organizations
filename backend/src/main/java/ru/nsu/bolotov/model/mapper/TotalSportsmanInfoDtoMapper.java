package ru.nsu.bolotov.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.bolotov.model.dto.sport.couch.CouchInfoDto;
import ru.nsu.bolotov.model.dto.sport.sportsman.SportsmanInfoDto;
import ru.nsu.bolotov.model.dto.sport.totalinfo.TotalSportsmanInfoDto;
import ru.nsu.bolotov.model.dto.sport.type.SportTypeInfoDto;
import ru.nsu.bolotov.model.entity.sport.TotalSportsmanInfo;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class TotalSportsmanInfoDtoMapper implements Function<TotalSportsmanInfo, TotalSportsmanInfoDto> {
    private final SportsmanInfoDtoMapper sportsmanInfoDtoMapper;
    private final SportTypeMapper sportTypeMapper;
    private final CouchMapper couchMapper;

    @Override
    public TotalSportsmanInfoDto apply(TotalSportsmanInfo totalSportsmanInfo) {
        SportsmanInfoDto sportsmanInfoDto = sportsmanInfoDtoMapper.apply(totalSportsmanInfo.getSportsman());
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
