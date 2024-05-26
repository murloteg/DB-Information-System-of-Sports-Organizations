package ru.nsu.bolotov.model.dto.sport.couch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.bolotov.model.enumeration.Sex;

@Getter
@Setter
@AllArgsConstructor
public class CouchInfoDto {
    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "last_name")
    private String lastName;

    @JsonProperty(value = "couch_id")
    private long couchId;

    private int age;
    private Sex sex;
}
