package ru.nsu.bolotov.model.dto.championship.organizer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChampionshipOrganizerStatisticInfoDto {
    @JsonProperty(value = "organizer_info")
    private ChampionshipOrganizerInfoDto championshipOrganizerInfoDto;

    @JsonProperty(value = "number_of_championships")
    private int numberOfChampionships;
}
