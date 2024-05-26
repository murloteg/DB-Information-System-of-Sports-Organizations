package ru.nsu.bolotov.model.dto.sport.totalinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TotalSportsmanInfoCreationDto {
    @JsonProperty(value = "sportsman_id")
    @Min(value = 0)
    @NotNull
    private long sportsmanId;

    @JsonProperty(value = "sport_type_id")
    @Min(value = 0)
    @NotNull
    private long sportTypeId;

    @JsonProperty(value = "couch_id")
    @Min(value = 0)
    @NotNull
    private long couchId;

    @JsonProperty(value = "sport_rank_level")
    @NotBlank
    private String sportRankLevel;
}
