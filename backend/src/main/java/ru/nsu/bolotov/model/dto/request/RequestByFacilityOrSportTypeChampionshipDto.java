package ru.nsu.bolotov.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestByFacilityOrSportTypeChampionshipDto {
    @JsonProperty(value = "sport_facility_id")
    @Min(0)
    @NotNull
    private long sportFacilityId;

    @JsonProperty(value = "sport_type_id")
    @Min(0)
    @NotNull
    private long sportTypeId;
}
