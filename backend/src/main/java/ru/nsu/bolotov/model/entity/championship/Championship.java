package ru.nsu.bolotov.model.entity.championship;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import ru.nsu.bolotov.model.entity.facility.SportFacility;
import ru.nsu.bolotov.model.entity.sport.Sportsman;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "championship")
public class Championship {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "championship_id")
    private long championshipId;

    @NotBlank
    @Length(max = 100)
    @Column(name = "championship_name")
    private String championshipName;

    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "championships")
    @EqualsAndHashCode.Exclude
    private List<ChampionshipOrganizer> organizers = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            joinColumns = {@JoinColumn(name = "championship_id")},
            inverseJoinColumns = {@JoinColumn(name = "sportsman_id")}
    )
    private List<Sportsman> participants = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            joinColumns = {@JoinColumn(name = "championship_id")},
            inverseJoinColumns = {@JoinColumn(name = "sportsman_id")}
    )
    private List<Sportsman> winners = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sport_facility_id")
    private SportFacility sportFacility;
}
