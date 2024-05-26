package ru.nsu.bolotov.dao.facility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.bolotov.model.entity.facility.SportCourt;
import ru.nsu.bolotov.model.enumeration.SportFacilityType;

import java.util.Optional;

@Repository
public interface SportCourtRepository extends JpaRepository<SportCourt, Long> {
    Optional<SportCourt> findSportCourtByFacilityId(long facilityId);

    boolean existsByFacilityId(long facilityId);

    void deleteByFacilityId(long facilityId);

    @Modifying
    @Query(value = "update SportCourt sc set sc.facilityName = ?1, sc.facilityType = ?2, sc.typeOfCoverage = ?3 where sc.facilityId = ?4")
    void updateByFacilityId(String facilityName, SportFacilityType facilityType, String typeOfCoverage, long facilityId);
}
