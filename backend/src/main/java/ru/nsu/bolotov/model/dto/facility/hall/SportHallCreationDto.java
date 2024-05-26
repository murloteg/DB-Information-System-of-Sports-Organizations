package ru.nsu.bolotov.model.dto.facility.hall;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.bolotov.model.dto.facility.GeneralSportFacilityCreationDto;

@Getter
@Setter
@AllArgsConstructor
public class SportHallCreationDto {
    @JsonProperty(value = "sport_facility")
    private GeneralSportFacilityCreationDto sportFacilityCreationDto;

    @JsonProperty(value = "number_of_seats")
    @Min(1)
    @NotNull
    private int numberOfSeats;
}
