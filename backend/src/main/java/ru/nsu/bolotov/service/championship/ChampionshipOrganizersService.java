package ru.nsu.bolotov.service.championship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.bolotov.dao.championship.ChampionshipOrganizersRepository;
import ru.nsu.bolotov.model.dto.championship.organizer.ChampionshipOrganizerCreationDto;
import ru.nsu.bolotov.model.dto.championship.organizer.ChampionshipOrganizerInfoDto;
import ru.nsu.bolotov.model.dto.championship.organizer.ChampionshipOrganizerUpdateDto;
import ru.nsu.bolotov.model.entity.championship.ChampionshipOrganizer;
import ru.nsu.bolotov.model.exception.championship.ChampionshipOrganizerNotFoundException;
import ru.nsu.bolotov.model.mapper.ChampionshipOrganizersMapper;

import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    public void deleteChampionshipOrganizer(long organizerId) {
        if (championshipOrganizersRepository.existsByOrganizerId(organizerId)) {
            championshipOrganizersRepository.deleteByOrganizerId(organizerId);
        } else {
            throw new ChampionshipOrganizerNotFoundException("Организатор соревнований не найден");
        }
    }

    @Transactional
    public void updateChampionshipOrganizer(ChampionshipOrganizerUpdateDto championshipOrganizerUpdateDto) {
        if (championshipOrganizersRepository.existsByOrganizerId(championshipOrganizerUpdateDto.getOrganizerId())) {
            championshipOrganizersRepository.updateChampionshipOrganizerByOrganizerId(
                    championshipOrganizerUpdateDto.getCompanyName(),
                    championshipOrganizerUpdateDto.getCompanyDescription(),
                    championshipOrganizerUpdateDto.getOrganizerId()
            );
        } else {
            throw new ChampionshipOrganizerNotFoundException("Организатор соревнований не найден");
        }
    }

    public List<ChampionshipOrganizerInfoDto> getOrganizers(Pageable pageable) {
        Page<ChampionshipOrganizer> organizers = championshipOrganizersRepository.findAll(pageable);
        List<ChampionshipOrganizerInfoDto> championshipOrganizerInfoDtos = new ArrayList<>();
        for (ChampionshipOrganizer championshipOrganizer : organizers) {
            championshipOrganizerInfoDtos.add(championshipOrganizersMapper.map(championshipOrganizer));
        }
        return championshipOrganizerInfoDtos;
    }
}
