package ru.nsu.bolotov.dao.sport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.bolotov.model.entity.sport.SportType;

import java.util.Optional;

@Repository
public interface SportTypeRepository extends JpaRepository<SportType, Long> {
    Optional<SportType> findSportTypeBySportTypeId(long sportTypeId);

    boolean existsBySportTypeId(long sportTypeId);

    void deleteBySportTypeId(long sportTypeId);

    @Modifying
    @Query(value = "update SportType st set st.sportName = ?1, st.sportDescription = ?2 where st.sportTypeId = ?3")
    void updateSportTypeBySportTypeId(String sportName, String sportDescription, long sportTypeId);
}
