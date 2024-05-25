package ru.nsu.bolotov.model.dto.facility;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SportHallInfoDto {
    @JsonProperty(value = "sport_facility")
    private GeneralSportFacilityInfoDto sportFacilityInfoDto;

    @JsonProperty(value = "number_of_seats")
    private int numberOfSeats;
}
