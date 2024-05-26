package ru.nsu.bolotov.model.dto.facility.court;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.bolotov.model.dto.facility.GeneralSportFacilityUpdateDto;

@Getter
@Setter
@AllArgsConstructor
public class SportCourtUpdateDto {
    @JsonProperty(value = "sport_facility")
    @NotNull
    private GeneralSportFacilityUpdateDto sportFacilityUpdateDto;

    @JsonProperty(value = "type_of_coverage")
    @NotBlank
    private String typeOfCoverage;
}
