package ru.nsu.bolotov.dao.facility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.bolotov.model.entity.facility.SportFacility;

import java.util.Optional;

@Repository
public interface SportFacilityRepository extends JpaRepository<SportFacility, Long> {
    Optional<SportFacility> findSportFacilityByFacilityId(long facilityId);
}
