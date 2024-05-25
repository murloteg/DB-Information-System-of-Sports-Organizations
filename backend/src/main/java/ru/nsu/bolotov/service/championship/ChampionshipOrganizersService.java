package ru.nsu.bolotov.service.championship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.bolotov.dao.championship.ChampionshipOrganizersRepository;
import ru.nsu.bolotov.model.dto.championship.ChampionshipOrganizerCreationDto;
import ru.nsu.bolotov.model.dto.championship.ChampionshipOrganizerInfoDto;
import ru.nsu.bolotov.model.entity.championship.ChampionshipOrganizer;
import ru.nsu.bolotov.model.exception.championship.ChampionshipOrganizerNotFoundException;
import ru.nsu.bolotov.model.mapper.ChampionshipOrganizersMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChampionshipOrganizersService {
    private final ChampionshipOrganizersRepository championshipOrganizersRepository;
    private final ChampionshipOrganizersMapper championshipOrganizersMapper;

    @Transactional
    public long createChampionShipOrganizer(ChampionshipOrganizerCreationDto championshipOrganizerCreationDto) {
        log.info("Received event for creation organizer with company name: {}", championshipOrganizerCreationDto.getCompanyName());
        ChampionshipOrganizer championshipOrganizer = championshipOrganizersMapper.map(championshipOrganizerCreationDto);
        ChampionshipOrganizer savedOrganizer = championshipOrganizersRepository.save(championshipOrganizer);
        return savedOrganizer.getOrganizerId();
    }

    public ChampionshipOrganizerInfoDto getChampionshipOrganizerInfo(long organizerId) {
        ChampionshipOrganizer championshipOrganizer = championshipOrganizersRepository.findChampionshipOrganizerByOrganizerId(organizerId)
                .orElseThrow(() -> new ChampionshipOrganizerNotFoundException("Организатор соревнований не найден"));
        return championshipOrganizersMapper.map(championshipOrganizer);
    }
}
