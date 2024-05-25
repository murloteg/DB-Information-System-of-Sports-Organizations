package ru.nsu.bolotov.dao.facility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.bolotov.model.entity.facility.SportHall;

import java.util.Optional;

@Repository
public interface SportHallRepository extends JpaRepository<SportHall, Long> {
    Optional<SportHall> findSportHallByFacilityId(long facilityId);
}
