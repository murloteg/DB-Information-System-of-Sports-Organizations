package ru.nsu.bolotov.dao.championship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.bolotov.model.entity.championship.Championship;

import java.util.Optional;

@Repository
public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
    Optional<Championship> findChampionshipByChampionshipId(long championshipId);
}
