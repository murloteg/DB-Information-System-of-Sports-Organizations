package ru.nsu.bolotov.model.dto.sport.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SportTypeCreationDto {
    @JsonProperty(value = "sport_name")
    @NotBlank
    private String sportName;

    @JsonProperty(value = "sport_description")
    @Nullable
    private String sportDescription;
}
