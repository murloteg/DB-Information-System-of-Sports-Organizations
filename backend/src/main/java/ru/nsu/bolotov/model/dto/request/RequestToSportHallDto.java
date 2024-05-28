package ru.nsu.bolotov.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.bolotov.model.enumeration.SportFacilityType;

@Getter
@Setter
@AllArgsConstructor
public class RequestToSportHallDto {
    @JsonProperty(value = "number_of_seats")
    @Min(0)
    @NotNull
    private int numberOfSeats;

    @JsonProperty(value = "facility_type")
    @NotNull
    private SportFacilityType sportFacilityType;
}
