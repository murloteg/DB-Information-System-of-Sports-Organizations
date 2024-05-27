package ru.nsu.bolotov.model.entity.sport;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "sport_type")
public class SportType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sport_type_id")
    private long sportTypeId;

    @Column(name = "sport_name", unique = true)
    @Length(min = 2, max = 80)
    @NotNull
    private String sportName;

    @Column(name = "sport_description")
    @Nullable
    private String sportDescription;
}
