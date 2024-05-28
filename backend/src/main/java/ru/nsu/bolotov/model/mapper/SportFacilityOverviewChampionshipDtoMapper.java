package ru.nsu.bolotov.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.bolotov.model.dto.championship.ChampionshipPeriodDto;
import ru.nsu.bolotov.model.dto.facility.SportFacilityOverviewChampionshipDto;
import ru.nsu.bolotov.model.entity.facility.SportFacility;
import ru.nsu.bolotov.model.mapper.facility.SportFacilityMapper;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class SportFacilityOverviewChampionshipDtoMapper implements Function<Map.Entry<SportFacility, List<ChampionshipPeriodDto>>, SportFacilityOverviewChampionshipDto> {
    private final SportFacilityMapper sportFacilityMapper;

    @Override
    public SportFacilityOverviewChampionshipDto apply(Map.Entry<SportFacility, List<ChampionshipPeriodDto>> entry) {
        SportFacility sportFacility = entry.getKey();
        return new SportFacilityOverviewChampionshipDto(
                sportFacilityMapper.map(sportFacility),
                entry.getValue()
        );
    }
}
