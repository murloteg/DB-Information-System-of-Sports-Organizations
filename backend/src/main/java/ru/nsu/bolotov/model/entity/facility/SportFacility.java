package ru.nsu.bolotov.model.entity.facility;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;
import ru.nsu.bolotov.model.entity.championship.Championship;
import ru.nsu.bolotov.model.enumeration.SportFacilityType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "sport_facility")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SportFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sport_facility_id")
    private long facilityId;

    @Column(name = "facility_name")
    @Length(min = 2, max = 180)
    @NotNull
    private String facilityName;

    @Column(name = "facility_type")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private SportFacilityType facilityType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sportFacility")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @EqualsAndHashCode.Exclude
    private List<Championship> championships = new ArrayList<>();
}
