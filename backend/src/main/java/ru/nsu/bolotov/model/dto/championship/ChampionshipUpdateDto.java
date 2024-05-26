package ru.nsu.bolotov.model.dto.championship;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ChampionshipUpdateDto {
    @JsonProperty(value = "championship_id")
    @Min(0)
    @NotNull
    private long championshipId;

    @JsonProperty(value = "championship_name")
    @NotBlank
    private String championshipName;

    @JsonProperty(value = "start_date")
    @NotNull
    private LocalDate startDate;

    @JsonProperty(value = "end_date")
    @NotNull
    private LocalDate endDate;

    @JsonProperty(value = "sport_facility_id")
    @Min(0)
    @NotNull
    private long sportFacilityId;

    @JsonProperty(value = "sport_type_id")
    @Min(0)
    @NotNull
    private long sportTypeId;

    @JsonDeserialize(as = ArrayList.class)
    @Size(min = 1)
    @NotNull
    private List<Long> organizers;
}
