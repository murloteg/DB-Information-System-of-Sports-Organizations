package ru.nsu.bolotov.model.dto.championship;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChampionshipOrganizerInfoDto {
    @JsonProperty(value = "company_name")
    private String companyName;

    @JsonProperty(value = "company_description")
    @Nullable
    private String companyDescription;

    @JsonProperty(value = "organizer_id")
    private long organizerId;
}
