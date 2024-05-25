package ru.nsu.bolotov.model.dto.facility;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GeneralSportFacilityCreationDto {
    @JsonProperty(value = "facility_name")
    @NotBlank
    private String facilityName;

    @JsonProperty(value = "facility_type")
    @NotBlank
    private String facilityType;
}
