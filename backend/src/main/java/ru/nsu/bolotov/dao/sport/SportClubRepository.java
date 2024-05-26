package ru.nsu.bolotov.dao.sport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.bolotov.model.entity.sport.SportClub;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SportClubRepository extends JpaRepository<SportClub, Long> {
    Optional<SportClub> findSportClubByClubId(Long clubId);

    boolean existsByClubId(long clubId);

    void deleteByClubId(long clubId);

    @Modifying
    @Query(value = "update SportClub sc set sc.clubName = ?1, sc.dateOfFoundation =?2 where sc.clubId = ?3")
    void updateSportClubByClubId(String clubName, LocalDate dateOfFoundation, long clubId);
}
