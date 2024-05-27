package ru.nsu.bolotov.service.sport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.bolotov.dao.sport.SportClubRepository;
import ru.nsu.bolotov.model.dto.sport.club.SportClubDto;
import ru.nsu.bolotov.model.dto.sport.club.SportClubUpdateDto;
import ru.nsu.bolotov.model.entity.sport.SportClub;
import ru.nsu.bolotov.model.exception.sport.SportClubNotFoundException;
import ru.nsu.bolotov.model.mapper.SportClubMapper;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SportClubService {
    private final SportClubRepository sportClubRepository;
    private final SportClubMapper sportClubMapper;

    @Transactional
    public long createSportClub(SportClubDto sportClubDto) {
        log.info("Received event for creation club with name: {}", sportClubDto.getClubName());
        SportClub sportClub = sportClubMapper.map(sportClubDto);
        SportClub savedClub = sportClubRepository.save(sportClub);
        return savedClub.getClubId();
    }

    public SportClubDto getSportClubInfo(long clubId) {
        SportClub sportClub = sportClubRepository.findSportClubByClubId(clubId)
                .orElseThrow(() -> new SportClubNotFoundException("Клуб не найден"));
        return sportClubMapper.map(sportClub);
    }

    @Transactional
    public void deleteSportClub(long clubId) {
        if (sportClubRepository.existsByClubId(clubId)) {
            sportClubRepository.deleteByClubId(clubId);
        } else {
            throw new SportClubNotFoundException("Клуб не найден");
        }
    }

    @Transactional
    public void updateSportClub(SportClubUpdateDto sportClubUpdateDto) {
        if (sportClubRepository.existsByClubId(sportClubUpdateDto.getClubId())) {
            sportClubRepository.updateSportClubByClubId(
                    sportClubUpdateDto.getClubName(),
                    sportClubUpdateDto.getDateOfFoundation(),
                    sportClubUpdateDto.getClubId()
            );
        } else {
            throw new SportClubNotFoundException("Клуб не найден");
        }
    }

    public List<SportClubDto> getSportClubs(Pageable pageable) {
        Page<SportClub> sportClubs = sportClubRepository.findAll(pageable);
        List<SportClubDto> sportClubDtos = new ArrayList<>();
        for (SportClub sportClub : sportClubs) {
            sportClubDtos.add(sportClubMapper.map(sportClub));
        }
        return sportClubDtos;
    }
}
