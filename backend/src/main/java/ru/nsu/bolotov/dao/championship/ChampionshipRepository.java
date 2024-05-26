package ru.nsu.bolotov.dao.championship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.bolotov.model.entity.championship.Championship;
import ru.nsu.bolotov.model.entity.facility.SportFacility;
import ru.nsu.bolotov.model.entity.sport.SportType;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
    Optional<Championship> findChampionshipByChampionshipId(long championshipId);

    boolean existsByChampionshipId(long championshipId);

    void deleteByChampionshipId(long championshipId);

    @Modifying
    @Query(value = "update Championship c set c.championshipName = ?1, c.startDate = ?2, c.endDate = ?3, c.sportFacility = ?4, c.sportType = ?5 where c.championshipId = ?6")
    void updateChampionshipByChampionshipId(String championshipName, LocalDate startDate, LocalDate endDate, SportFacility sportFacility, SportType sportType, long championshipId);
}
