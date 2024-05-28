package ru.nsu.bolotov.model.mapper;

import org.springframework.stereotype.Component;
import ru.nsu.bolotov.model.dto.sport.club.SportClubStatisticInfoDto;
import ru.nsu.bolotov.model.entity.sport.SportClub;

import java.util.function.Function;

/**
 * Note: this mapper only creates new sport club statistic info dto without info about members
 */
@Component
public class SportClubStatisticInfoDtoMapper implements Function<SportClub, SportClubStatisticInfoDto> {
    @Override
    public SportClubStatisticInfoDto apply(SportClub sportClub) {
        return new SportClubStatisticInfoDto(
                sportClub.getClubId(),
                sportClub.getClubName(),
                sportClub.getDateOfFoundation(),
                1
        );
    }
}
