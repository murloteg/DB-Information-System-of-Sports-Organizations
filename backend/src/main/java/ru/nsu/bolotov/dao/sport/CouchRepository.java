package ru.nsu.bolotov.dao.sport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.bolotov.model.entity.sport.Couch;

import java.util.Optional;

@Repository
public interface CouchRepository extends JpaRepository<Couch, Long> {
    Optional<Couch> findCouchByCouchId(long couchId);
}
