package ru.nsu.bolotov.dao.championship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.bolotov.model.entity.championship.ChampionshipOrganizer;

import java.util.Optional;

@Repository
public interface ChampionshipOrganizersRepository extends JpaRepository<ChampionshipOrganizer, Long> {
    Optional<ChampionshipOrganizer> findChampionshipOrganizerByOrganizerId(long organizerId);
    ChampionshipOrganizer getChampionshipOrganizerByOrganizerId(long organizerId);
    boolean existsByOrganizerId(long organizerId);
}
