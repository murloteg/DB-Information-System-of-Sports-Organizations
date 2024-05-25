package ru.nsu.bolotov.model.dto.sport;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.bolotov.model.enumeration.Sex;

@Getter
@Setter
@AllArgsConstructor
public class CouchCreationDto {
    @JsonProperty(value = "first_name")
    @NotBlank
    private String firstName;

    @JsonProperty(value = "last_name")
    @NotBlank
    private String lastName;

    @Min(value = 5)
    @Max(value = 105)
    private int age;

    private Sex sex;
}
