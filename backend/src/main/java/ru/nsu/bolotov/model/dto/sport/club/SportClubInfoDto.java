package ru.nsu.bolotov.model.dto.sport.club;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class SportClubInfoDto {
    @JsonProperty(value = "club_id")
    @NotNull
    private long clubId;

    @JsonProperty(value = "club_name")
    @NotBlank
    private String clubName;

    @JsonProperty(value = "date_of_foundation")
    private LocalDate dateOfFoundation;
}
