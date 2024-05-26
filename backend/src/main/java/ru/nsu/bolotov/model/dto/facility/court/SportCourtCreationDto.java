package ru.nsu.bolotov.model.dto.facility.court;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.bolotov.model.dto.facility.GeneralSportFacilityCreationDto;

@Getter
@Setter
@AllArgsConstructor
public class SportCourtCreationDto {
    @JsonProperty(value = "sport_facility")
    private GeneralSportFacilityCreationDto sportFacilityCreationDto;

    @JsonProperty(value = "type_of_coverage")
    @NotBlank
    private String typeOfCoverage;
}
