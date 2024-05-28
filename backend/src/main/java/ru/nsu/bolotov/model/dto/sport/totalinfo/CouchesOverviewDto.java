package ru.nsu.bolotov.model.dto.sport.totalinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.bolotov.model.dto.sport.couch.CouchOverviewDto;
import ru.nsu.bolotov.model.dto.sport.sportsman.SportsmanInfoDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CouchesOverviewDto {
    @JsonProperty(value = "sportsman_info")
    private SportsmanInfoDto sportsmanInfoDto;

    @JsonProperty(value = "couches")
    private List<CouchOverviewDto> couchInfoDto;
}
