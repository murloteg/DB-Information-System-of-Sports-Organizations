package ru.nsu.bolotov.dao.facility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.bolotov.model.entity.facility.SportHall;
import ru.nsu.bolotov.model.enumeration.SportFacilityType;

import java.util.List;
import java.util.Optional;

@Repository
public interface SportHallRepository extends JpaRepository<SportHall, Long> {
    Optional<SportHall> findSportHallByFacilityId(long facilityId);

    boolean existsByFacilityId(long facilityId);

    void deleteByFacilityId(long facilityId);

    @Modifying
    @Query(value = "update SportHall sh set sh.facilityName = ?1, sh.facilityType = ?2, sh.numberOfSeats = ?3 where sh.facilityId = ?4")
    void updateByFacilityId(String facilityName, SportFacilityType facilityType, int numberOfSeats, long facilityId);

    @Query(value = "select sh from SportHall sh where sh.numberOfSeats >= ?1 or sh.facilityType = ?2")
    List<SportHall> getAllByPropertyOrByFacilityType(int numberOfSeats, SportFacilityType sportFacilityType);
}
