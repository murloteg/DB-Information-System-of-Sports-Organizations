package ru.nsu.bolotov.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.bolotov.model.dto.championship.organizer.ChampionshipOrganizerInfoDto;
import ru.nsu.bolotov.model.dto.championship.organizer.ChampionshipOrganizerStatisticInfoDto;
import ru.nsu.bolotov.model.entity.championship.ChampionshipOrganizer;

import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ChampionshipOrganizerStatisticInfoDtoMapper implements Function<Map.Entry<ChampionshipOrganizer, Integer>, ChampionshipOrganizerStatisticInfoDto> {
    private final ChampionshipOrganizersMapper championshipOrganizersMapper;

    @Override
    public ChampionshipOrganizerStatisticInfoDto apply(Map.Entry<ChampionshipOrganizer, Integer> entry) {
        ChampionshipOrganizerInfoDto championshipOrganizerInfoDto = championshipOrganizersMapper.map(entry.getKey());
        return new ChampionshipOrganizerStatisticInfoDto(
                championshipOrganizerInfoDto,
                entry.getValue()
        );
    }
}
