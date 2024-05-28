package ru.nsu.bolotov.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.bolotov.model.dto.sport.sportsman.SportsmanManySportTypesOverviewDto;
import ru.nsu.bolotov.model.dto.sport.totalinfo.TotalSportsmanInfoDto;
import ru.nsu.bolotov.model.entity.sport.TotalSportsmanInfo;

import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class SportsmanManySportTypesDtoMapper implements Function<TotalSportsmanInfo, SportsmanManySportTypesOverviewDto> {
    private final TotalSportsmanInfoDtoMapper totalSportsmanInfoDtoMapper;

    @Override
    public SportsmanManySportTypesOverviewDto apply(TotalSportsmanInfo totalSportsmanInfo) {
        TotalSportsmanInfoDto totalSportsmanInfoDto = totalSportsmanInfoDtoMapper.apply(totalSportsmanInfo);
        return new SportsmanManySportTypesOverviewDto(
                totalSportsmanInfoDto.getSportsmanInfoDto(),
                List.of(totalSportsmanInfoDto.getSportTypeInfoDto())
        );
    }
}
