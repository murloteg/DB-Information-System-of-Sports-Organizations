package ru.nsu.bolotov.model.entity.sport;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "sport_club")
public class SportClub {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "club_id")
    private long clubId;

    @Column(name = "club_name")
    @Length(min = 2, max = 100)
    @NotNull
    private String clubName;

    @Column(name = "date_of_foundation")
    @NotNull
    private LocalDate dateOfFoundation;

    @OneToMany(mappedBy = "sportClub", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @EqualsAndHashCode.Exclude
    private List<Sportsman> clubMembers = new ArrayList<>();
}
