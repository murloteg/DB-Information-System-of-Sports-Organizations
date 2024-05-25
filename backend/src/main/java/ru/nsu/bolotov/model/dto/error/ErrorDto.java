package ru.nsu.bolotov.model.dto.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDto {
    private String message;

    @JsonProperty(value = "time")
    private LocalDateTime localDateTime;
}
