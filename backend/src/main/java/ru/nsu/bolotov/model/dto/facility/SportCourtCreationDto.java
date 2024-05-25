package ru.nsu.bolotov.model.dto.facility;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
