package ru.nsu.bolotov.model.dto.championship.organizer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChampionshipOrganizerUpdateDto {
    @JsonProperty(value = "organizer_id")
    @Min(0)
    @NotNull
    private long organizerId;

    @JsonProperty(value = "company_name")
    @NotBlank
    private String companyName;

    @JsonProperty(value = "company_description")
    @Nullable
    private String companyDescription;
}
