package ru.nsu.bolotov.model.entity.facility;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "sport_court")
public class SportCourt extends SportFacility {
    @Column(name = "type_of_coverage")
    @NotBlank
    private String typeOfCoverage;
}
