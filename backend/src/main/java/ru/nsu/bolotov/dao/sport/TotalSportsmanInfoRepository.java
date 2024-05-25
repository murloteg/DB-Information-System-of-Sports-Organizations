package ru.nsu.bolotov.dao.sport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.bolotov.model.entity.sport.TotalSportsmanInfo;

import java.util.Optional;

@Repository
public interface TotalSportsmanInfoRepository extends JpaRepository<TotalSportsmanInfo, Long> {
    Optional<TotalSportsmanInfo> findTotalSportsmanInfoByTotalSportsmanInfoId(long totalSportsmanInfoId);
}
