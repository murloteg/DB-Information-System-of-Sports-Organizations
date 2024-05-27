package ru.nsu.bolotov.model.dto.sport.totalinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.bolotov.model.dto.sport.sportsman.SportsmanInfoDto;
import ru.nsu.bolotov.model.dto.sport.couch.CouchInfoDto;
import ru.nsu.bolotov.model.dto.sport.type.SportTypeInfoDto;

@Getter
@Setter
@AllArgsConstructor
public class TotalSportsmanInfoDto {
    @JsonProperty(value = "total_sportsman_info_id")
    private long totalSportsmanInfoId;

    @JsonProperty(value = "sportsman_info")
    private SportsmanInfoDto sportsmanInfoDto;

    @JsonProperty(value = "sport_type_info")
    private SportTypeInfoDto sportTypeInfoDto;

    @JsonProperty(value = "couch_info")
    private CouchInfoDto couchInfoDto;

    @JsonProperty(value = "sport_rank_level")
    private String sportRankLevel;
}
