package ru.nsu.bolotov.model.dto.championship;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.bolotov.model.entity.championship.ChampionshipOrganizer;
import ru.nsu.bolotov.model.entity.facility.SportFacility;
import ru.nsu.bolotov.model.entity.sport.SportType;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ChampionshipValidatedCreationDto {
    private String championshipName;
    private LocalDate startDate;
    private LocalDate endDate;
    private SportFacility sportFacility;
    private SportType sportType;
    private List<ChampionshipOrganizer> organizers;
}
