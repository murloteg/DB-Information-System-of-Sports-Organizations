package ru.nsu.bolotov.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class RequestByPeriodDto {
    @JsonProperty(value = "start_date")
    @NotNull
    private LocalDate startDate;

    @JsonProperty(value = "end_date")
    @NotNull
    private LocalDate endDate;
}
