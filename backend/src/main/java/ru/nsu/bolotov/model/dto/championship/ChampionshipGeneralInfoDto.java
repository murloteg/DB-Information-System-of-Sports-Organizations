package ru.nsu.bolotov.model.dto.championship;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.bolotov.model.dto.facility.GeneralSportFacilityInfoDto;
import ru.nsu.bolotov.model.dto.sport.type.SportTypeInfoDto;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ChampionshipGeneralInfoDto {
    @JsonProperty(value = "championship_name")
    private String championshipName;

    @JsonProperty(value = "start_date")
    private LocalDate startDate;

    @JsonProperty(value = "end_date")
    private LocalDate endDate;

    @JsonProperty(value = "sport_type")
    private SportTypeInfoDto sportTypeInfoDto;

    @JsonProperty(value = "sport_facility")
    private GeneralSportFacilityInfoDto sportFacilityInfoDto;
}
