package ru.nsu.bolotov.service.sport;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.bolotov.dao.sport.SportClubRepository;
import ru.nsu.bolotov.dao.sport.SportsmanRepository;
import ru.nsu.bolotov.model.dto.sport.SportClubDto;
import ru.nsu.bolotov.model.dto.sport.SportsmanCreationDto;
import ru.nsu.bolotov.model.dto.sport.SportsmanInfoDto;
import ru.nsu.bolotov.model.entity.sport.SportClub;
import ru.nsu.bolotov.model.entity.sport.Sportsman;
import ru.nsu.bolotov.model.exception.sport.SportClubNotFoundException;
import ru.nsu.bolotov.model.exception.sport.SportsmanNotFoundException;
import ru.nsu.bolotov.model.mapper.SportClubMapper;
import ru.nsu.bolotov.model.mapper.SportsmanMapper;

@Service
@RequiredArgsConstructor
public class SportsmanService {
    private final SportsmanRepository sportsmanRepository;
    private final SportClubRepository sportClubRepository;
    private final SportsmanMapper sportsmanMapper;
    private final SportClubMapper sportClubMapper;

    @Transactional
    public long createSportsman(SportsmanCreationDto sportsmanDto) {
        SportClub sportClub = sportClubRepository.findSportClubByClubId(sportsmanDto.getSportClubId())
                .orElseThrow(() -> new SportClubNotFoundException("Клуб не был найден"));
        Sportsman sportsman = sportsmanMapper.map(sportsmanDto);
        sportsman.setSportClub(sportClub);
        Sportsman savedSportsman = sportsmanRepository.save(sportsman);
        return savedSportsman.getSportsmanId();
    }

    public SportsmanInfoDto getSportsmanInfo(long sportsmanId) {
        Sportsman sportsman = sportsmanRepository.findSportsmanBySportsmanId(sportsmanId)
                .orElseThrow(() -> new SportsmanNotFoundException("Спортсмен не найден"));
        SportClub sportClub = sportsman.getSportClub();
        SportClubDto sportClubDto = sportClubMapper.map(sportClub);
        SportsmanInfoDto sportsmanInfoDto = sportsmanMapper.map(sportsman);
        sportsmanInfoDto.setSportClubDto(sportClubDto);
        return sportsmanInfoDto;
    }
}
