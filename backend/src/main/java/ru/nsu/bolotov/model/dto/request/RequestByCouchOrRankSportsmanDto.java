package ru.nsu.bolotov.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.bolotov.model.enumeration.SportRankLevel;

@Getter
@Setter
@AllArgsConstructor
public class RequestByCouchOrRankSportsmanDto {
    @JsonProperty(value = "couch_id")
    @Min(0)
    @NotNull
    private long couchId;

    @JsonProperty(value = "sport_rank_level")
    @NotNull
    private SportRankLevel sportRankLevel;
}
