package ru.nsu.bolotov.model.entity.facility;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "sport_hall")
public class SportHall extends SportFacility {
    @Column(name = "number_of_seats")
    @Min(value = 1)
    @Max(value = 300000)
    @NotNull
    private int numberOfSeats;
}
