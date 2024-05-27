package ru.nsu.bolotov.model.dto.championship;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ChampionshipParticipantsUpdateDto {
    @JsonProperty(value = "championship_id")
    @Min(0)
    @NotNull
    private long championshipId;

    @JsonProperty(value = "participants")
    @JsonDeserialize(as = ArrayList.class)
    @NotNull
    private List<Long> participantIds;
}
