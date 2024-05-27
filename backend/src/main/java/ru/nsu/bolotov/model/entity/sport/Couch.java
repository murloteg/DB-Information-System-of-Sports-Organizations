package ru.nsu.bolotov.model.entity.sport;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import ru.nsu.bolotov.model.enumeration.Sex;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "couch")
public class Couch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couch_id")
    private long couchId;

    @Column(name = "first_name")
    @Length(min = 1, max = 50)
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @Length(min = 1, max = 50)
    @NotNull
    private String lastName;

    @Column(name = "age")
    @Min(value = 5)
    @Max(value = 105)
    @NotNull
    private int age;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "sex")
    @NotNull
    private Sex sex;
}
