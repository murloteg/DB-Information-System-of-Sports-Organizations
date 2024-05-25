package ru.nsu.bolotov.dao.sport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.bolotov.model.entity.sport.Sportsman;

import java.util.Optional;

@Repository
public interface SportsmanRepository extends JpaRepository<Sportsman, Long> {
    Optional<Sportsman> findSportsmanBySportsmanId(long sportsmanId);
}
