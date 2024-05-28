package ru.nsu.bolotov.model.dto.sport.couch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.bolotov.model.enumeration.Sex;

@Getter
@Setter
@AllArgsConstructor
public class CouchOverviewDto {
    @JsonProperty(value = "couch_id")
    private long couchId;

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "last_name")
    private String lastName;

    private int age;

    private Sex sex;

    @JsonProperty(value = "sport_type_id")
    private long sportTypeId;
}
