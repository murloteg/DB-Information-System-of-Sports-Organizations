package ru.nsu.bolotov.model.dto.championship;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.bolotov.model.dto.championship.organizer.ChampionshipOrganizerInfoDto;
import ru.nsu.bolotov.model.dto.facility.GeneralSportFacilityInfoDto;
import ru.nsu.bolotov.model.dto.sport.sportsman.SportsmanInfoDto;
import ru.nsu.bolotov.model.dto.sport.type.SportTypeInfoDto;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ChampionshipExtendedInfoDto {
    @JsonProperty(value = "championship_id")
    private long championshipId;

    @JsonProperty(value = "championship_name")
    private String championshipName;

    @JsonProperty(value = "start_date")
    private LocalDate startDate;

    @JsonProperty(value = "end_date")
    private LocalDate endDate;

    @JsonProperty(value = "sport_facility")
    private GeneralSportFacilityInfoDto sportFacilityInfoDto;

    @JsonProperty(value = "sport_type")
    private SportTypeInfoDto sportTypeInfoDto;

    private List<ChampionshipOrganizerInfoDto> organizers;

    private List<SportsmanInfoDto> participants;

    private List<SportsmanInfoDto> winners;
}
