package ru.nsu.bolotov.service.sport;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.bolotov.dao.sport.SportClubRepository;
import ru.nsu.bolotov.dao.sport.SportsmanRepository;
import ru.nsu.bolotov.model.dto.sport.club.SportClubDto;
import ru.nsu.bolotov.model.dto.sport.sportsman.SportsmanCreationDto;
import ru.nsu.bolotov.model.dto.sport.sportsman.SportsmanInfoDto;
import ru.nsu.bolotov.model.dto.sport.sportsman.SportsmanUpdateDto;
import ru.nsu.bolotov.model.entity.sport.SportClub;
import ru.nsu.bolotov.model.entity.sport.Sportsman;
import ru.nsu.bolotov.model.exception.sport.SportClubNotFoundException;
import ru.nsu.bolotov.model.exception.sport.SportsmanNotFoundException;
import ru.nsu.bolotov.model.mapper.SportClubMapper;
import ru.nsu.bolotov.model.mapper.SportsmanMapper;

import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    public void deleteSportsman(long sportsmanId) {
        if (sportsmanRepository.existsBySportsmanId(sportsmanId)) {
            sportsmanRepository.deleteBySportsmanId(sportsmanId);
        } else {
            throw new SportsmanNotFoundException("Спортсмен не найден");
        }
    }

    @Transactional
    public void updateSportsman(SportsmanUpdateDto sportsmanUpdateDto) {
        SportClub sportClub = sportClubRepository.findSportClubByClubId(sportsmanUpdateDto.getSportClubId())
                .orElseThrow(() -> new SportClubNotFoundException("Спортивный клуб не найден"));
        if (sportsmanRepository.existsBySportsmanId(sportsmanUpdateDto.getSportsmanId())) {
            sportsmanRepository.updateSportsmanBySportsmanId(
                    sportsmanUpdateDto.getFirstName(),
                    sportsmanUpdateDto.getLastName(),
                    sportsmanUpdateDto.getBiography(),
                    sportsmanUpdateDto.getAge(),
                    sportsmanUpdateDto.getSex(),
                    sportClub,
                    sportsmanUpdateDto.getSportsmanId()
            );
        } else {
            throw new SportsmanNotFoundException("Спортсмен не найден");
        }
    }

    public List<SportsmanInfoDto> getSportsmen(Pageable pageable) {
        Page<Sportsman> sportsmen = sportsmanRepository.findAll(pageable);
        List<SportsmanInfoDto> sportsmanInfoDtos = new ArrayList<>();
        for (Sportsman sportsman : sportsmen) {
            SportClub sportClub = sportsman.getSportClub();
            SportClubDto sportClubDto = sportClubMapper.map(sportClub);
            SportsmanInfoDto sportsmanInfoDto = sportsmanMapper.map(sportsman);
            sportsmanInfoDto.setSportClubDto(sportClubDto);
            sportsmanInfoDtos.add(sportsmanInfoDto);
        }
        return sportsmanInfoDtos;
    }
}
