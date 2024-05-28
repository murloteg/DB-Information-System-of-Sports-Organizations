package ru.nsu.bolotov.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.bolotov.model.enumeration.SportFacilityType;

@Getter
@Setter
@AllArgsConstructor
public class RequestToSportCourtDto {
    @JsonProperty(value = "type_of_coverage")
    @NotBlank
    private String typeOfCoverage;

    @JsonProperty(value = "facility_type")
    @NotNull
    private SportFacilityType sportFacilityType;
}
