package ru.nsu.bolotov.model.entity.championship;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "championship_organizers")
public class ChampionshipOrganizer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "organizer_id")
    private long organizerId;

    @Column(name = "company_name", unique = true)
    @NotBlank
    private String companyName;

    @Column(name = "company_description")
    @Nullable
    private String companyDescription;

    @ManyToMany
    @JoinTable(
            joinColumns = {@JoinColumn(name = "organizer_id")},
            inverseJoinColumns = {@JoinColumn(name = "championship_id")}
    )
    @EqualsAndHashCode.Exclude
    private List<Championship> championships = new ArrayList<>();
}
