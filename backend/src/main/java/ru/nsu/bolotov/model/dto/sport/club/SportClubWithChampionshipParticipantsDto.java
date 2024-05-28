package ru.nsu.bolotov.model.dto.sport.club;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.bolotov.model.dto.sport.sportsman.SportsmanInfoDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SportClubWithChampionshipParticipantsDto {
    private SportClubInfoDto sportClubInfoDto;

    private List<SportsmanInfoDto> participants;
}
