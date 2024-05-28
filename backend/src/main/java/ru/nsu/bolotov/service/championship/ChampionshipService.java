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
import ru.nsu.bolotov.model.dto.facility.SportFacilityOverviewChampionshipDto;
import ru.nsu.bolotov.model.dto.request.RequestByFacilityOrSportTypeChampionshipDto;
import ru.nsu.bolotov.model.dto.request.RequestByPeriodDto;
import ru.nsu.bolotov.model.dto.request.RequestByPeriodOrOrganizerChampionshipDto;
import ru.nsu.bolotov.model.dto.sport.club.SportClubStatisticInfoDto;
import ru.nsu.bolotov.model.dto.sport.sportsman.SportsmanInfoDto;
import ru.nsu.bolotov.model.entity.championship.Championship;
import ru.nsu.bolotov.model.entity.championship.ChampionshipOrganizer;
import ru.nsu.bolotov.model.entity.facility.SportFacility;
import ru.nsu.bolotov.model.entity.sport.SportClub;
import ru.nsu.bolotov.model.entity.sport.SportType;
import ru.nsu.bolotov.model.entity.sport.Sportsman;
import ru.nsu.bolotov.model.entity.sport.TotalSportsmanInfo;
import ru.nsu.bolotov.model.exception.championship.ChampionshipNotFoundException;
import ru.nsu.bolotov.model.exception.championship.ChampionshipOrganizerNotFoundException;
import ru.nsu.bolotov.model.exception.championship.IllegalAttemptToAddSportsmanToChampionship;
import ru.nsu.bolotov.model.exception.facility.SportFacilityNotFoundException;
import ru.nsu.bolotov.model.exception.sport.SportClubNotFoundException;
import ru.nsu.bolotov.model.exception.sport.SportTypeNotFoundException;
import ru.nsu.bolotov.model.exception.sport.SportsmanNotFoundException;
import ru.nsu.bolotov.model.mapper.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final ChampionshipExtendedInfoDtoMapper championshipExtendedInfoDtoMapper;
    private final SportClubStatisticInfoDtoMapper sportClubStatisticInfoDtoMapper;
    private final SportsmanInfoDtoMapper sportsmanInfoDtoMapper;
    private final SportFacilityOverviewChampionshipDtoMapper sportFacilityOverviewChampionshipDtoMapper;

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
        return championshipExtendedInfoDtoMapper.apply(championship);
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
            championshipExtendedInfoDtos.add(championshipExtendedInfoDtoMapper.apply(championship));
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
        championship.getParticipants().clear();
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

    public List<ChampionshipExtendedInfoDto> getChampionshipsByPeriodOrOrganizer(
            RequestByPeriodOrOrganizerChampionshipDto requestByPeriodOrOrganizerChampionship
    ) {
        ChampionshipOrganizer organizer = championshipOrganizersRepository
                .findChampionshipOrganizerByOrganizerId(requestByPeriodOrOrganizerChampionship.getOrganizerId())
                .orElseThrow(() -> new ChampionshipOrganizerNotFoundException("Организатор соревнований не найден"));
        List<Championship> championshipsByPeriod = championshipRepository.getAllByPeriod(
                requestByPeriodOrOrganizerChampionship.getStartDate(),
                requestByPeriodOrOrganizerChampionship.getEndDate()
        );
        List<ChampionshipExtendedInfoDto> championshipExtendedInfoDtos = new ArrayList<>();
        for (Championship championship : championshipsByPeriod) {
            championshipExtendedInfoDtos.add(championshipExtendedInfoDtoMapper.apply(championship));
        }

        List<Championship> championshipByOrganizers = championshipRepository.findChampionshipByOrganizersContains(organizer);
        for (Championship championship : championshipByOrganizers) {
            championshipExtendedInfoDtos.add(championshipExtendedInfoDtoMapper.apply(championship));
        }
        return championshipExtendedInfoDtos;
    }

    public List<ChampionshipExtendedInfoDto> getChampionshipsByFacilityOrSportType(
            RequestByFacilityOrSportTypeChampionshipDto requestByFacilityOrSportTypeChampionshipDto
    ) {
        SportType sportType = sportTypeRepository.findSportTypeBySportTypeId(requestByFacilityOrSportTypeChampionshipDto.getSportTypeId())
                .orElseThrow(() -> new SportTypeNotFoundException("Вид спорта не найден"));
        SportFacility sportFacility = sportFacilityRepository.findSportFacilityByFacilityId(requestByFacilityOrSportTypeChampionshipDto.getSportFacilityId())
                .orElseThrow(() -> new SportFacilityNotFoundException("Спортивное сооружение не найдено"));
        List<Championship> championships = championshipRepository.getAllBySportTypeOrSportFacility(sportType, sportFacility);
        List<ChampionshipExtendedInfoDto> championshipInfoDtos = new ArrayList<>();
        for (Championship championship : championships) {
            championshipInfoDtos.add(championshipExtendedInfoDtoMapper.apply(championship));
        }
        return championshipInfoDtos;
    }

    public List<SportClubStatisticInfoDto> getSportClubsByPeriod(RequestByPeriodDto requestByPeriodDto) {
        List<Championship> championships = championshipRepository.getAllByPeriod(
                requestByPeriodDto.getStartDate(),
                requestByPeriodDto.getEndDate()
        );
        List<SportClubStatisticInfoDto> sportClubs = new ArrayList<>();
        for (Championship championship : championships) {
            List<Sportsman> participants = championship.getParticipants();
            for (Sportsman participant : participants) {
                SportClub sportClub = participant.getSportClub();
                if (!isSportClubAlreadyContainsAtSportClubs(sportClub, sportClubs)) {
                    sportClubs.add(sportClubStatisticInfoDtoMapper.apply(sportClub));
                } else {
                    SportClubStatisticInfoDto sportClubStatistic = findSportClubStatisticByClub(sportClub, sportClubs);
                    sportClubStatistic.setNumberOfMembers(sportClubStatistic.getNumberOfMembers() + 1);
                }
            }
        }
        return sportClubs;
    }

    public List<SportsmanInfoDto> getInactiveSportsmenByPeriod(RequestByPeriodDto requestByPeriodDto) {
        List<Championship> championships = championshipRepository.getAllByPeriod(
                requestByPeriodDto.getStartDate(),
                requestByPeriodDto.getEndDate()
        );
        List<Sportsman> participants = new ArrayList<>();
        for (Championship championship : championships) {
            participants.addAll(
                    championship.getParticipants().stream()
                            .filter(participant -> !participants.contains(participant))
                            .toList()
            );
        }
        List<Sportsman> inactiveSportsmen = sportsmanRepository.getAllByNotContainsInParticipants(participants);
        List<SportsmanInfoDto> sportsmanInfoDtos = new ArrayList<>();
        for (Sportsman sportsman : inactiveSportsmen) {
            sportsmanInfoDtos.add(sportsmanInfoDtoMapper.apply(sportsman));
        }
        return sportsmanInfoDtos;
    }

    public List<SportFacilityOverviewChampionshipDto> getSportFacilitiesByPeriod(RequestByPeriodDto requestByPeriodDto) {
        List<Championship> championships = championshipRepository.getAllByPeriod(
                requestByPeriodDto.getStartDate(),
                requestByPeriodDto.getEndDate()
        );
        Map<SportFacility, List<ChampionshipPeriodDto>> sportFacilities = new HashMap<>();
        for (Championship championship : championships) {
            SportFacility sportFacility = championship.getSportFacility();
            ChampionshipPeriodDto championshipPeriodDto = new ChampionshipPeriodDto(championship.getStartDate(), championship.getEndDate());
            if (sportFacilities.containsKey(sportFacility)) {
                List<ChampionshipPeriodDto> previousPeriods = sportFacilities.get(sportFacility);
                List<ChampionshipPeriodDto> updatedPeriods = new ArrayList<>(previousPeriods);
                updatedPeriods.add(championshipPeriodDto);
                sportFacilities.replace(sportFacility, updatedPeriods);
            } else {
                sportFacilities.put(sportFacility, List.of(championshipPeriodDto));
            }
        }

        List<SportFacilityOverviewChampionshipDto> sportFacilityOverviewDtos = new ArrayList<>();
        for (Map.Entry<SportFacility, List<ChampionshipPeriodDto>> entry : sportFacilities.entrySet()) {
            sportFacilityOverviewDtos.add(sportFacilityOverviewChampionshipDtoMapper.apply(entry));
        }
        return sportFacilityOverviewDtos;
    }

    private boolean isSportsmanHaveSameSportTypeAsChampionship(List<TotalSportsmanInfo> infos, SportType championshipSportType) {
        return infos.stream()
                .anyMatch(totalSportsmanInfo -> championshipSportType.getSportName().equals(totalSportsmanInfo.getSportType().getSportName()));
    }

    private boolean isSportsmanAlreadyTakePartInChampionship(Sportsman sportsman, Championship championship) {
        return championship.getParticipants().stream()
                .anyMatch(participant -> participant.getSportsmanId() != sportsman.getSportsmanId());
    }

    private boolean isWinnerInParticipantsOfChampionship(Sportsman winner, List<Sportsman> participants) {
        return participants.stream()
                .anyMatch(participant -> participant.getSportsmanId() == winner.getSportsmanId());
    }

    private void removeChampionshipFromOrganizers(List<ChampionshipOrganizer> organizers, Championship championship) {
        for (ChampionshipOrganizer organizer : organizers) {
            organizer.getChampionships().remove(championship);
            championshipOrganizersRepository.save(organizer);
        }
    }

    private boolean isSportClubAlreadyContainsAtSportClubs(SportClub sportClub, List<SportClubStatisticInfoDto> sportClubs) {
        return sportClubs.stream()
                .anyMatch(sportClubDto -> sportClubDto.getClubId() == sportClub.getClubId());
    }

    private SportClubStatisticInfoDto findSportClubStatisticByClub(SportClub sportClub, List<SportClubStatisticInfoDto> sportClubs) {
        return sportClubs.stream()
                .filter(sportClubStatistic -> sportClubStatistic.getClubId() == sportClub.getClubId())
                .findFirst().orElseThrow(() -> new SportClubNotFoundException("Спортивный клуб не был найден"));
    }
}
