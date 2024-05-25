package ru.nsu.bolotov.dao.facility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.bolotov.model.entity.facility.SportCourt;

import java.util.Optional;

@Repository
public interface SportCourtRepository extends JpaRepository<SportCourt, Long> {
    Optional<SportCourt> findSportCourtByFacilityId(long facilityId);
}
