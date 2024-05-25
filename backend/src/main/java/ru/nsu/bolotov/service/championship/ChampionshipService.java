package ru.nsu.bolotov.service.championship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.bolotov.dao.championship.ChampionshipOrganizersRepository;
import ru.nsu.bolotov.dao.championship.ChampionshipRepository;
import ru.nsu.bolotov.dao.facility.SportFacilityRepository;
import ru.nsu.bolotov.dao.sport.SportTypeRepository;
import ru.nsu.bolotov.model.dto.championship.*;
import ru.nsu.bolotov.model.entity.championship.Championship;
import ru.nsu.bolotov.model.entity.championship.ChampionshipOrganizer;
import ru.nsu.bolotov.model.entity.facility.SportFacility;
import ru.nsu.bolotov.model.entity.sport.SportType;
import ru.nsu.bolotov.model.exception.championship.ChampionshipNotFoundException;
import ru.nsu.bolotov.model.exception.championship.ChampionshipOrganizerNotFoundException;
import ru.nsu.bolotov.model.exception.facility.SportFacilityNotFoundException;
import ru.nsu.bolotov.model.exception.sport.SportTypeNotFoundException;
import ru.nsu.bolotov.model.mapper.ChampionshipMapper;
import ru.nsu.bolotov.model.mapper.ChampionshipOrganizersMapper;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChampionshipService {
    private final ChampionshipRepository championshipRepository;
    private final ChampionshipOrganizersRepository championshipOrganizersRepository;
    private final SportFacilityRepository sportFacilityRepository;
    private final SportTypeRepository sportTypeRepository;
    private final ChampionshipMapper championshipMapper;
    private final ChampionshipOrganizersMapper championshipOrganizersMapper;

    @Transactional
    public long createChampionship(ChampionshipCreationDto championshipCreationDto) {
        log.info("Received event for championship with name: {}", championshipCreationDto.getChampionshipName());
        SportFacility sportFacility = sportFacilityRepository.findSportFacilityByFacilityId(championshipCreationDto.getSportFacilityId())
                .orElseThrow(() -> new SportFacilityNotFoundException("Спортивное сооружение не найдено"));
        SportType sportType = sportTypeRepository.findSportTypeBySportTypeId(championshipCreationDto.getSportTypeId())
                .orElseThrow(() -> new SportTypeNotFoundException("Вид спорта не был найден"));
        List<ChampionshipOrganizer> organizers = new ArrayList<>();
        for (long organizerId : championshipCreationDto.getOrganizers()) {
            if (championshipOrganizersRepository.existsByOrganizerId(organizerId)) {
                organizers.add(championshipOrganizersRepository.getChampionshipOrganizerByOrganizerId(organizerId));
            } else {
                throw new ChampionshipOrganizerNotFoundException("Организатор соревнований не был найден");
            }
        }
        ChampionshipValidatedCreationDto championshipValidatedCreationDto = new ChampionshipValidatedCreationDto(
                championshipCreationDto.getChampionshipName(),
                championshipCreationDto.getStartDate(),
                championshipCreationDto.getEndDate(),
                sportFacility,
                sportType,
                organizers
        );
        Championship championship = championshipMapper.map(championshipValidatedCreationDto);
        Championship savedChampionship = championshipRepository.save(championship);
        for (ChampionshipOrganizer organizer : organizers) {
            organizer.getChampionships().add(championship);
            championshipOrganizersRepository.save(organizer);
        }
        return savedChampionship.getChampionshipId();
    }

    public ChampionshipExtendedInfoDto getChampionshipInfo(long championshipId) {
        Championship championship = championshipRepository.findChampionshipByChampionshipId(championshipId)
                .orElseThrow(() -> new ChampionshipNotFoundException("Соревнование не было найдено"));
        ChampionshipGeneralInfoDto championshipGeneralInfoDto = championshipMapper.map(championship);

        List<ChampionshipOrganizer> organizers = championship.getOrganizers();
        List<ChampionshipOrganizerInfoDto> organizerInfoDtos = new ArrayList<>();
        for (ChampionshipOrganizer organizer : organizers) {
            organizerInfoDtos.add(championshipOrganizersMapper.map(organizer));
        }
        return new ChampionshipExtendedInfoDto(
                championshipGeneralInfoDto.getChampionshipName(),
                championshipGeneralInfoDto.getStartDate(),
                championshipGeneralInfoDto.getEndDate(),
                championshipGeneralInfoDto.getSportFacilityInfoDto(),
                championshipGeneralInfoDto.getSportTypeInfoDto(),
                organizerInfoDtos
        );
    }
}
