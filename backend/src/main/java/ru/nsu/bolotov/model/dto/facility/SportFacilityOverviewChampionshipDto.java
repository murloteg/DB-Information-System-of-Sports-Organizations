package ru.nsu.bolotov.model.dto.facility;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.bolotov.model.dto.championship.ChampionshipPeriodDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SportFacilityOverviewChampionshipDto {
    @JsonProperty(value = "sport_facility")
    private GeneralSportFacilityInfoDto generalSportFacilityInfoDto;

    @JsonProperty(value = "championship_periods")
    private List<ChampionshipPeriodDto> championshipPeriods;
}
