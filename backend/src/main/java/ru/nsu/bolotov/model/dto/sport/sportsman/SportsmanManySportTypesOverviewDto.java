package ru.nsu.bolotov.model.dto.sport.sportsman;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.bolotov.model.dto.sport.type.SportTypeInfoDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SportsmanManySportTypesOverviewDto {
    @JsonProperty(value = "sportsman_info")
    private SportsmanInfoDto sportsmanInfoDto;

    @JsonProperty(value = "sport_types")
    private List<SportTypeInfoDto> sportTypeInfoDto;
}
