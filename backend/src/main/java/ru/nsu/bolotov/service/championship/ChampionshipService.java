package ru.nsu.bolotov.service.championship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.bolotov.dao.championship.ChampionshipOrganizersRepository;
import ru.nsu.bolotov.dao.championship.ChampionshipRepository;
import ru.nsu.bolotov.dao.facility.SportFacilityRepository;
import ru.nsu.bolotov.dao.sport.SportTypeRepository;
import ru.nsu.bolotov.dao.sport.SportsmanRepository;
import ru.nsu.bolotov.dao.sport.TotalSportsmanInfoRepository;
import ru.nsu.bolotov.model.dto.championship.*;
import ru.nsu.bolotov.model.entity.championship.Championship;
import ru.nsu.bolotov.model.entity.championship.ChampionshipOrganizer;
import ru.nsu.bolotov.model.entity.facility.SportFacility;
import ru.nsu.bolotov.model.entity.sport.SportType;
import ru.nsu.bolotov.model.entity.sport.Sportsman;
import ru.nsu.bolotov.model.entity.sport.TotalSportsmanInfo;
import ru.nsu.bolotov.model.exception.championship.ChampionshipNotFoundException;
import ru.nsu.bolotov.model.exception.championship.ChampionshipOrganizerNotFoundException;
import ru.nsu.bolotov.model.exception.championship.IllegalAttemptToAddSportsmanToChampionship;
import ru.nsu.bolotov.model.exception.facility.SportFacilityNotFoundException;
import ru.nsu.bolotov.model.exception.sport.SportTypeNotFoundException;
import ru.nsu.bolotov.model.exception.sport.SportsmanNotFoundException;
import ru.nsu.bolotov.model.mapper.ChampionshipExtendedInfoMapper;
import ru.nsu.bolotov.model.mapper.ChampionshipMapper;

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
    private final SportsmanRepository sportsmanRepository;
    private final TotalSportsmanInfoRepository totalSportsmanInfoRepository;
    private final ChampionshipMapper championshipMapper;
    private final ChampionshipExtendedInfoMapper championshipExtendedInfoMapper;

    @Transactional
    public long createChampionship(ChampionshipCreationDto championshipCreationDto) {
        log.info("Received event for championship with name: {}", championshipCreationDto.getChampionshipName());
        SportFacility sportFacility = sportFacilityRepository.findSportFacilityByFacilityId(championshipCreationDto.getSportFacilityId())
                .orElseThrow(() -> new SportFacilityNotFoundException("Спортивное сооружение не найдено"));
        SportType sportType = sportTypeRepository.findSportTypeBySportTypeId(championshipCreationDto.getSportTypeId())
                .orElseThrow(() -> new SportTypeNotFoundException("Вид спорта не был найден"));
        List<ChampionshipOrganizer> organizers = new ArrayList<>();
        for (long organizerId : championshipCreationDto.getOrganizers()) {
            ChampionshipOrganizer championshipOrganizer = championshipOrganizersRepository.findChampionshipOrganizerByOrganizerId(organizerId)
                    .orElseThrow(() -> new ChampionshipOrganizerNotFoundException("Организатор соревнований не найден"));
            organizers.add(championshipOrganizer);
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
        return championshipExtendedInfoMapper.apply(championship);
    }

    @Transactional
    public void deleteChampionship(long championshipId) {
        if (championshipRepository.existsByChampionshipId(championshipId)) {
            championshipRepository.deleteByChampionshipId(championshipId);
        } else {
            throw new ChampionshipNotFoundException("Соревнование не было найдено");
        }
    }

    @Transactional
    public void updateChampionship(ChampionshipUpdateDto championshipUpdateDto) {
        SportFacility sportFacility = sportFacilityRepository.findSportFacilityByFacilityId(championshipUpdateDto.getSportFacilityId())
                .orElseThrow(() -> new SportFacilityNotFoundException("Спортивное сооружение не найдено"));
        SportType sportType = sportTypeRepository.findSportTypeBySportTypeId(championshipUpdateDto.getSportTypeId())
                .orElseThrow(() -> new SportTypeNotFoundException("Вид спорта не был найден"));
        List<ChampionshipOrganizer> organizers = new ArrayList<>();
        for (long organizerId : championshipUpdateDto.getOrganizers()) {
            ChampionshipOrganizer championshipOrganizer = championshipOrganizersRepository.findChampionshipOrganizerByOrganizerId(organizerId)
                    .orElseThrow(() -> new ChampionshipOrganizerNotFoundException("Организатор соревнований не найден"));
            organizers.add(championshipOrganizer);
        }
        Championship championship = championshipRepository.findChampionshipByChampionshipId(championshipUpdateDto.getChampionshipId())
                .orElseThrow(() -> new ChampionshipNotFoundException("Соревнование не было найдено"));
        List<ChampionshipOrganizer> previousOrganizers = championship.getOrganizers();
        removeChampionshipFromOrganizers(previousOrganizers, championship);
        championship.setChampionshipName(championshipUpdateDto.getChampionshipName());
        championship.setStartDate(championshipUpdateDto.getStartDate());
        championship.setEndDate(championshipUpdateDto.getEndDate());
        championship.setSportFacility(sportFacility);
        championship.setSportType(sportType);
        Championship updatedChampionship = championshipRepository.save(championship);
        for (ChampionshipOrganizer organizer : organizers) {
            organizer.getChampionships().add(updatedChampionship);
            championshipOrganizersRepository.save(organizer);
        }
    }

    public List<ChampionshipExtendedInfoDto> getChampionships(Pageable pageable) {
        Page<Championship> championships = championshipRepository.findAll(pageable);
        List<ChampionshipExtendedInfoDto> championshipExtendedInfoDtos = new ArrayList<>();
        for (Championship championship : championships) {
            championshipExtendedInfoDtos.add(championshipExtendedInfoMapper.apply(championship));
        }
        return championshipExtendedInfoDtos;
    }

    @Transactional
    public void addParticipantsToChampionship(ChampionshipParticipantsUpdateDto championshipParticipantsUpdateDto) {
        Championship championship = championshipRepository.findChampionshipByChampionshipId(championshipParticipantsUpdateDto.getChampionshipId())
                .orElseThrow(() -> new ChampionshipNotFoundException("Соревнование не было найдено"));
        List<Sportsman> sportsmen = new ArrayList<>();
        for (Long sportsmanId : championshipParticipantsUpdateDto.getParticipantIds()) {
            Sportsman sportsman = sportsmanRepository.findSportsmanBySportsmanId(sportsmanId)
                    .orElseThrow(() -> new SportsmanNotFoundException("Спортсмен не найден"));
            sportsmen.add(sportsman);
        }
        for (Sportsman sportsman : sportsmen) {
            List<TotalSportsmanInfo> infos = totalSportsmanInfoRepository.findTotalSportsmanInfoBySportsmanSportsmanId(sportsman.getSportsmanId());
            if (!isSportsmanHaveSameSportTypeAsChampionship(infos, championship.getSportType())) {
                throw new IllegalAttemptToAddSportsmanToChampionship("Спортсмен не имеет права участвовать в соревновании");
            }
            championship.getParticipants().add(sportsman);
        }
        championshipRepository.save(championship);
    }

    @Transactional
    public void addWinnersToChampionship(ChampionshipWinnersUpdateDto championshipWinnersUpdateDto) {
        Championship championship = championshipRepository.findChampionshipByChampionshipId(championshipWinnersUpdateDto.getChampionshipId())
                .orElseThrow(() -> new ChampionshipNotFoundException("Соревнование не было найдено"));
        List<Sportsman> sportsmen = new ArrayList<>();
        for (Long sportsmanId : championshipWinnersUpdateDto.getWinnerIds()) {
            Sportsman sportsman = sportsmanRepository.findSportsmanBySportsmanId(sportsmanId)
                    .orElseThrow(() -> new SportsmanNotFoundException("Спортсмен не найден"));
            sportsmen.add(sportsman);
        }
        for (Sportsman sportsman : sportsmen) {
            if (!isWinnerInParticipantsOfChampionship(sportsman, championship.getParticipants())) {
                throw new IllegalAttemptToAddSportsmanToChampionship("Спортсмен не участвует в соревновании");
            }
            championship.getWinners().add(sportsman);
        }
        championshipRepository.save(championship);
    }

    private boolean isSportsmanHaveSameSportTypeAsChampionship(List<TotalSportsmanInfo> infos, SportType championshipSportType) {
        return infos.stream().anyMatch(totalSportsmanInfo -> championshipSportType.getSportName().equals(totalSportsmanInfo.getSportType().getSportName()));
    }

    private boolean isWinnerInParticipantsOfChampionship(Sportsman winner, List<Sportsman> participants) {
        return participants.stream().anyMatch(participant -> participant.getSportsmanId() == winner.getSportsmanId());
    }

    private void removeChampionshipFromOrganizers(List<ChampionshipOrganizer> organizers, Championship championship) {
        for (ChampionshipOrganizer organizer : organizers) {
            organizer.getChampionships().remove(championship);
            championshipOrganizersRepository.save(organizer);
        }
    }
}
