package ru.nsu.bolotov.model.entity.sport;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.nsu.bolotov.model.enumeration.SportRankLevel;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "total_sportsman_info")
public class TotalSportsmanInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long totalSportsmanInfoId;

    @ManyToOne
    @JoinColumn(name = "sportsman_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Sportsman sportsman;

    @ManyToOne
    @JoinColumn(name = "sport_type_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SportType sportType;

    @ManyToOne
    @JoinColumn(name = "couch_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Couch couch;

    @Column(name = "sport_rank_level")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private SportRankLevel sportRankLevel;

    public TotalSportsmanInfo(Sportsman sportsman, SportType sportType, Couch couch, SportRankLevel sportRankLevel) {
        this.sportsman = sportsman;
        this.sportType = sportType;
        this.couch = couch;
        this.sportRankLevel = sportRankLevel;
    }
}
