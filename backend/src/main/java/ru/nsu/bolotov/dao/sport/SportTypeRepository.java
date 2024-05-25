package ru.nsu.bolotov.dao.sport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.bolotov.model.entity.sport.SportType;

import java.util.Optional;

@Repository
public interface SportTypeRepository extends JpaRepository<SportType, Long> {
    Optional<SportType> findSportTypeBySportTypeId(long sportTypeId);
}
