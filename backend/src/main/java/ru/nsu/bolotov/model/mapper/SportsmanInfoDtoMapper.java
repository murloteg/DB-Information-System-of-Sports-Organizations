package ru.nsu.bolotov.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.bolotov.model.dto.sport.club.SportClubInfoDto;
import ru.nsu.bolotov.model.dto.sport.sportsman.SportsmanInfoDto;
import ru.nsu.bolotov.model.entity.sport.Sportsman;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class SportsmanInfoDtoMapper implements Function<Sportsman, SportsmanInfoDto> {
    private final SportClubMapper sportClubMapper;

    @Override
    public SportsmanInfoDto apply(Sportsman sportsman) {
        SportClubInfoDto sportClubInfoDto = sportClubMapper.map(sportsman.getSportClub());
        return new SportsmanInfoDto(
                sportsman.getSportsmanId(),
                sportsman.getFirstName(),
                sportsman.getLastName(),
                sportsman.getBiography(),
                sportClubInfoDto,
                sportsman.getAge(),
                sportsman.getSex()
        );
    }
}
