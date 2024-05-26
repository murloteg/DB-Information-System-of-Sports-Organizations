package ru.nsu.bolotov.model.dto.sport.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SportTypeUpdateDto {
    @JsonProperty(value = "sport_type_id")
    @NotNull
    private long sportTypeId;

    @JsonProperty(value = "sport_name")
    @NotBlank
    private String sportName;

    @JsonProperty(value = "sport_description")
    @Nullable
    private String sportDescription;
}
