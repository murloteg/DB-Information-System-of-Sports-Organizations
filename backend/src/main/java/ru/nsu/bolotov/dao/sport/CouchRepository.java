package ru.nsu.bolotov.dao.sport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.bolotov.model.entity.sport.Couch;
import ru.nsu.bolotov.model.enumeration.Sex;

import java.util.Optional;

@Repository
public interface CouchRepository extends JpaRepository<Couch, Long> {
    Optional<Couch> findCouchByCouchId(long couchId);

    boolean existsByCouchId(long couchId);

    void deleteByCouchId(long couchId);

    @Modifying
    @Query(value = "update Couch c set c.firstName = ?1, c.lastName = ?2, c.age = ?3, c.sex = ?4 where c.couchId = ?5")
    void updateCouchByCouchId(String firstName, String lastName, int age, Sex sex, long couchId);
}
