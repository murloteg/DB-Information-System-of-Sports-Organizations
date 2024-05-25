package ru.nsu.bolotov.model.dto.championship;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
public class ChampionshipCreationDto {
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
    @NotNull
    private long sportFacilityId;

    @JsonDeserialize(as = ArrayList.class)
    @Size(min = 1)
    @NotNull
    private List<Long> organizers;
}
