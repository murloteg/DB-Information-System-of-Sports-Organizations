package ru.nsu.bolotov.model.dto.championship;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChampionshipOrganizerCreationDto {
    @JsonProperty(value = "company_name")
    @NotBlank
    private String companyName;

    @JsonProperty(value = "company_description")
    @Nullable
    private String companyDescription;
}
