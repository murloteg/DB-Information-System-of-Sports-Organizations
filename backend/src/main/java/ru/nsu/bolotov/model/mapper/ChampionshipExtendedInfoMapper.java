package ru.nsu.bolotov.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.bolotov.model.dto.championship.ChampionshipExtendedInfoDto;
import ru.nsu.bolotov.model.dto.championship.ChampionshipGeneralInfoDto;
import ru.nsu.bolotov.model.dto.championship.organizer.ChampionshipOrganizerInfoDto;
import ru.nsu.bolotov.model.dto.sport.club.SportClubDto;
import ru.nsu.bolotov.model.dto.sport.sportsman.SportsmanInfoDto;
import ru.nsu.bolotov.model.entity.championship.Championship;
import ru.nsu.bolotov.model.entity.championship.ChampionshipOrganizer;
import ru.nsu.bolotov.model.entity.sport.Sportsman;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ChampionshipExtendedInfoMapper implements Function<Championship, ChampionshipExtendedInfoDto> {
    private final ChampionshipMapper championshipMapper;
    private final ChampionshipOrganizersMapper championshipOrganizersMapper;
    private final SportsmanMapper sportsmanMapper;
    private final SportClubMapper sportClubMapper;

    @Override
    public ChampionshipExtendedInfoDto apply(Championship championship) {
        ChampionshipGeneralInfoDto championshipGeneralInfoDto = championshipMapper.map(championship);

        List<ChampionshipOrganizer> organizers = championship.getOrganizers();
        List<ChampionshipOrganizerInfoDto> organizerInfoDtos = new ArrayList<>();
        for (ChampionshipOrganizer organizer : organizers) {
            organizerInfoDtos.add(championshipOrganizersMapper.map(organizer));
        }

        List<Sportsman> participants = championship.getParticipants();
        List<SportsmanInfoDto> participantInfoDtos = new ArrayList<>();
        for (Sportsman participant : participants) {
            SportsmanInfoDto sportsmanInfoDto = sportsmanMapper.map(participant);
            SportClubDto sportClubDto = sportClubMapper.map(participant.getSportClub());
            sportsmanInfoDto.setSportClubDto(sportClubDto);
            participantInfoDtos.add(sportsmanInfoDto);
        }

        List<Sportsman> winners = championship.getWinners();
        List<SportsmanInfoDto> winnerInfoDtos = new ArrayList<>();
        for (Sportsman winner : winners) {
            SportsmanInfoDto sportsmanInfoDto = sportsmanMapper.map(winner);
            SportClubDto sportClubDto = sportClubMapper.map(winner.getSportClub());
            sportsmanInfoDto.setSportClubDto(sportClubDto);
            winnerInfoDtos.add(sportsmanInfoDto);
        }
        return new ChampionshipExtendedInfoDto(
                championshipGeneralInfoDto.getChampionshipId(),
                championshipGeneralInfoDto.getChampionshipName(),
                championshipGeneralInfoDto.getStartDate(),
                championshipGeneralInfoDto.getEndDate(),
                championshipGeneralInfoDto.getSportFacilityInfoDto(),
                championshipGeneralInfoDto.getSportTypeInfoDto(),
                organizerInfoDtos,
                participantInfoDtos,
                winnerInfoDtos
        );
    }
}
