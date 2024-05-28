package ru.nsu.bolotov.dao.championship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.bolotov.model.entity.championship.Championship;
import ru.nsu.bolotov.model.entity.championship.ChampionshipOrganizer;
import ru.nsu.bolotov.model.entity.facility.SportFacility;
import ru.nsu.bolotov.model.entity.sport.SportType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
    Optional<Championship> findChampionshipByChampionshipId(long championshipId);

    boolean existsByChampionshipId(long championshipId);

    void deleteByChampionshipId(long championshipId);

    @Modifying
    @Query(value = """
            update Championship c
            set c.championshipName = ?1, c.startDate = ?2, c.endDate = ?3, c.sportFacility = ?4, c.sportType = ?5
            where c.championshipId = ?6
            """)
    void updateChampionshipByChampionshipId(String championshipName, LocalDate startDate, LocalDate endDate, SportFacility sportFacility, SportType sportType, long championshipId);

    @Query(value = """
            select c from Championship c
            where c.startDate >= ?1 and c.endDate <= ?2
            """)
    List<Championship> getAllByPeriod(LocalDate startDate, LocalDate endDate);

    List<Championship> findChampionshipByOrganizersContains(ChampionshipOrganizer championshipOrganizer);

    @Query(value = """
            select c from Championship c
            where c.sportType = ?1 or c.sportFacility = ?2
            """)
    List<Championship> getAllBySportTypeOrSportFacility(SportType sportType, SportFacility sportFacility);
}
