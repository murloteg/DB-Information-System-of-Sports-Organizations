package ru.nsu.bolotov.model.dto.sport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TotalSportsmanInfoDto {
    @JsonProperty(value = "sportsman_info")
    private SportsmanInfoDto sportsmanInfoDto;

    @JsonProperty(value = "sport_type_info")
    private SportTypeInfoDto sportTypeInfoDto;

    @JsonProperty(value = "couch_info")
    private CouchInfoDto couchInfoDto;

    @JsonProperty(value = "sport_rank_level")
    private String sportRankLevel;
}
