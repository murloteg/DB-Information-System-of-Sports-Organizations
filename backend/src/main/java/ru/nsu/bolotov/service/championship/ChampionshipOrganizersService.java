package ru.nsu.bolotov.service.championship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.bolotov.dao.championship.ChampionshipOrganizersRepository;
import ru.nsu.bolotov.dao.championship.ChampionshipRepository;
import ru.nsu.bolotov.model.dto.championship.organizer.ChampionshipOrganizerCreationDto;
import ru.nsu.bolotov.model.dto.championship.organizer.ChampionshipOrganizerInfoDto;
import ru.nsu.bolotov.model.dto.championship.organizer.ChampionshipOrganizerStatisticInfoDto;
import ru.nsu.bolotov.model.dto.championship.organizer.ChampionshipOrganizerUpdateDto;
import ru.nsu.bolotov.model.dto.request.RequestByPeriodDto;
import ru.nsu.bolotov.model.entity.championship.Championship;
import ru.nsu.bolotov.model.entity.championship.ChampionshipOrganizer;
import ru.nsu.bolotov.model.exception.championship.ChampionshipOrganizerNotFoundException;
import ru.nsu.bolotov.model.mapper.ChampionshipOrganizerStatisticInfoDtoMapper;
import ru.nsu.bolotov.model.mapper.ChampionshipOrganizersMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChampionshipOrganizersService {
    private final ChampionshipOrganizersRepository championshipOrganizersRepository;
    private final ChampionshipRepository championshipRepository;
    private final ChampionshipOrganizersMapper championshipOrganizersMapper;
    private final ChampionshipOrganizerStatisticInfoDtoMapper championshipOrganizerStatisticInfoDtoMapper;

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

    public List<ChampionshipOrganizerStatisticInfoDto> getOrganizersStatisticsByPeriod(RequestByPeriodDto requestByPeriodDto) {
        List<Championship> championships = championshipRepository.getAllByPeriod(
                requestByPeriodDto.getStartDate(),
                requestByPeriodDto.getEndDate()
        );
        List<ChampionshipOrganizer> championshipOrganizers = new ArrayList<>();
        for (Championship championship : championships) {
            championshipOrganizers.addAll(
                    championship.getOrganizers().stream()
                            .filter(organizer -> !championshipOrganizers.contains(organizer))
                            .toList()
            );
        }

        Map<ChampionshipOrganizer, Integer> championshipOrganizersMap = new HashMap<>();
        for (ChampionshipOrganizer organizer : championshipOrganizers) {
            championshipOrganizersMap.put(organizer, 0);
        }
        for (Championship championship : championships) {
            championship.getOrganizers().forEach(organizer -> {
                        int previousNumberOfChampionships = championshipOrganizersMap.get(organizer);
                        championshipOrganizersMap.replace(organizer, previousNumberOfChampionships + 1);
                    }
            );
        }

        List<ChampionshipOrganizerStatisticInfoDto> organizersStatistics = new ArrayList<>();
        for (Map.Entry<ChampionshipOrganizer, Integer> entry : championshipOrganizersMap.entrySet()) {
            organizersStatistics.add(championshipOrganizerStatisticInfoDtoMapper.apply(entry));
        }
        return organizersStatistics;
    }
}
