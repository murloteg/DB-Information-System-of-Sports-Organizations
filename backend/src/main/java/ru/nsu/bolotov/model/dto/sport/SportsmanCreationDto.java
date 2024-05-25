package ru.nsu.bolotov.model.dto.sport;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.bolotov.model.enumeration.Sex;

@Getter
@Setter
@AllArgsConstructor
public class SportsmanCreationDto {
    @JsonProperty(value = "first_name")
    @NotBlank
    private String firstName;

    @JsonProperty(value = "last_name")
    @NotBlank
    private String lastName;

    @Min(value = 5)
    @Max(value = 105)
    private int age;

    @JsonProperty(value = "sport_club_id")
    @Min(value = 1)
    @NotNull
    private long sportClubId;

    private Sex sex;
    private String biography;
}
