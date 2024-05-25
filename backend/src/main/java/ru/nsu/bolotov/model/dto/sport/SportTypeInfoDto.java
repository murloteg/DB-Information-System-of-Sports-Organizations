package ru.nsu.bolotov.model.dto.sport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SportTypeInfoDto {
    @JsonProperty(value = "sport_name")
    private String sportName;

    @JsonProperty(value = "sport_description")
    private String sportDescription;

    @JsonProperty(value = "sport_type_id")
    private long sportTypeId;
}
