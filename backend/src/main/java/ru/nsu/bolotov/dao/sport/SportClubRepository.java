package ru.nsu.bolotov.dao.sport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.bolotov.model.entity.sport.SportClub;

import java.util.Optional;

@Repository
public interface SportClubRepository extends JpaRepository<SportClub, Long> {
    Optional<SportClub> findSportClubByClubId(Long clubId);
}
