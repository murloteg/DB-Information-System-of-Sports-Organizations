package ru.nsu.bolotov.dao.championship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.bolotov.model.entity.championship.ChampionshipOrganizer;

import java.util.Optional;

@Repository
public interface ChampionshipOrganizersRepository extends JpaRepository<ChampionshipOrganizer, Long> {
    Optional<ChampionshipOrganizer> findChampionshipOrganizerByOrganizerId(long organizerId);

    boolean existsByOrganizerId(long organizerId);

    void deleteByOrganizerId(long organizerId);

    @Modifying
    @Query(value = "update ChampionshipOrganizer co set co.companyName = ?1, co.companyDescription = ?2 where co.organizerId = ?3")
    void updateChampionshipOrganizerByOrganizerId(String companyName, String companyDescription, long organizerId);
}
