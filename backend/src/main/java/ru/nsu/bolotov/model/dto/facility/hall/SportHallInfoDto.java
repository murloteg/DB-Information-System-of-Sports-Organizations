package ru.nsu.bolotov.model.dto.facility.hall;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.bolotov.model.dto.facility.GeneralSportFacilityInfoDto;

@Getter
@Setter
@AllArgsConstructor
public class SportHallInfoDto {
    @JsonProperty(value = "sport_facility")
    private GeneralSportFacilityInfoDto sportFacilityInfoDto;

    @JsonProperty(value = "number_of_seats")
    private int numberOfSeats;
}
