package ru.nsu.bolotov.service.sport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.bolotov.dao.sport.SportClubRepository;
import ru.nsu.bolotov.model.dto.sport.SportClubDto;
import ru.nsu.bolotov.model.entity.sport.SportClub;
import ru.nsu.bolotov.model.exception.sport.SportClubNotFoundException;
import ru.nsu.bolotov.model.mapper.SportClubMapper;

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
}
