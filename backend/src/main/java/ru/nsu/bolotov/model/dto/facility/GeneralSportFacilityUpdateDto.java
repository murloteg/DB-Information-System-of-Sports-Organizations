package ru.nsu.bolotov.model.dto.facility;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GeneralSportFacilityUpdateDto {
    @JsonProperty(value = "facility_id")
    @Min(0)
    @NotNull
    private long facilityId;

    @JsonProperty(value = "facility_name")
    @NotBlank
    private String facilityName;

    @JsonProperty(value = "facility_type")
    @NotBlank
    private String facilityType;
}
