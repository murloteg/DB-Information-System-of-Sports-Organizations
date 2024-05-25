package ru.nsu.bolotov.model.dto.sport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SportRankLevelDto {
    @JsonProperty(value = "sport_rank_level")
    private String sportRankName;
}
