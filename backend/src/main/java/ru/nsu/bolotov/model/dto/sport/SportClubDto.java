package ru.nsu.bolotov.model.dto.sport;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class SportClubDto {
    @JsonProperty(value = "club_name")
    @NotBlank
    private String clubName;

    @JsonProperty(value = "date_of_foundation")
    private LocalDate dateOfFoundation;
}
