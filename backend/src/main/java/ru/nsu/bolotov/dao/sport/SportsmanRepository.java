package ru.nsu.bolotov.dao.sport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.bolotov.model.entity.sport.SportClub;
import ru.nsu.bolotov.model.entity.sport.Sportsman;
import ru.nsu.bolotov.model.enumeration.Sex;

import java.util.List;
import java.util.Optional;

@Repository
public interface SportsmanRepository extends JpaRepository<Sportsman, Long> {
    Optional<Sportsman> findSportsmanBySportsmanId(long sportsmanId);

    boolean existsBySportsmanId(long sportsmanId);

    void deleteBySportsmanId(long sportsmanId);

    @Modifying
    @Query(value = "update Sportsman s set s.firstName = ?1, s.lastName = ?2, s.biography = ?3, s.age = ?4, s.sex = ?5, s.sportClub = ?6 where s.sportsmanId = ?7")
    void updateSportsmanBySportsmanId(String firstName, String lastName, String biography, int age, Sex sex, SportClub sportClub, long sportsmanId);

    @Query(value = """
            select s from Sportsman s
            where s not in ?1
            """)
    List<Sportsman> getAllByNotContainsInParticipants(List<Sportsman> participants);
}
