package ru.nsu.bolotov.dao.sport;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.bolotov.model.entity.sport.Couch;
import ru.nsu.bolotov.model.entity.sport.SportType;
import ru.nsu.bolotov.model.entity.sport.Sportsman;
import ru.nsu.bolotov.model.entity.sport.TotalSportsmanInfo;
import ru.nsu.bolotov.model.enumeration.SportRankLevel;

import java.util.List;
import java.util.Optional;

@Repository
public interface TotalSportsmanInfoRepository extends JpaRepository<TotalSportsmanInfo, Long> {
    Optional<TotalSportsmanInfo> findTotalSportsmanInfoByTotalSportsmanInfoId(long totalSportsmanInfoId);

    List<TotalSportsmanInfo> findTotalSportsmanInfoBySportsmanSportsmanId(long sportsmanId);

    boolean existsByTotalSportsmanInfoId(long totalSportsmanInfoId);

    void deleteByTotalSportsmanInfoId(long totalSportsmanInfoId);

    @Modifying
    @Query(value = """
            update TotalSportsmanInfo tsi
            set tsi.sportsman = ?1, tsi.sportType = ?2, tsi.couch = ?3, tsi.sportRankLevel = ?4
            where tsi.totalSportsmanInfoId = ?5
            """)
    void updateTotalSportsmanInfoByTotalSportsmanInfoId(Sportsman sportsman, SportType sportType, Couch couch, SportRankLevel sportRankLevel, long totalSportsmanInfoId);

    @Query(value = "select tsi from TotalSportsmanInfo tsi where tsi.sportType = ?1")
    List<TotalSportsmanInfo> getAllBySportType(SportType sportType);

    @Query(value = "select tsi from TotalSportsmanInfo tsi where tsi.couch = ?1")
    List<TotalSportsmanInfo> getAllByCouch(Couch couch);

    @Query(value = "select tsi from TotalSportsmanInfo tsi where tsi.sportRankLevel = ?1")
    List<TotalSportsmanInfo> getAllBySportRankLevel(SportRankLevel sportRankLevel);

    @Query(value = """
            select tsi.sportsman from TotalSportsmanInfo tsi
            group by tsi.sportsman
            having count(distinct tsi.sportType) >= 2
            """)
    List<Sportsman> getSportsmenWithManySportTypes();

    List<TotalSportsmanInfo> findTotalSportsmanInfoBySportsman(Sportsman sportsman);

    @Query(value = "select tsi from TotalSportsmanInfo tsi where tsi.sportsman = ?1")
    List<TotalSportsmanInfo> getAllBySportsman(Sportsman sportsman);

    @Query(value = """
            select tsi.couch from TotalSportsmanInfo tsi
            where tsi.sportType = ?1
            """)
    List<Couch> getAllCouchesBySportType(SportType sportType);
}
