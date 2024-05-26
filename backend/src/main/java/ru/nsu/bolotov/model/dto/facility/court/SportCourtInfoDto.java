package ru.nsu.bolotov.model.dto.facility.court;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.bolotov.model.dto.facility.GeneralSportFacilityInfoDto;

@Getter
@Setter
@AllArgsConstructor
public class SportCourtInfoDto {
    @JsonProperty(value = "sport_facility")
    private GeneralSportFacilityInfoDto sportFacilityInfoDto;

    @JsonProperty(value = "type_of_coverage")
    private String typeOfCoverage;
}
