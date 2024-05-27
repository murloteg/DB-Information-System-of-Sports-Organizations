package ru.nsu.bolotov.model.dto.sport.sportsman;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.bolotov.model.dto.sport.club.SportClubInfoDto;
import ru.nsu.bolotov.model.enumeration.Sex;

@Getter
@Setter
@AllArgsConstructor
public class SportsmanInfoDto {
    @JsonProperty(value = "sportsman_id")
    private long sportsmanId;

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "last_name")
    private String lastName;
    
    private String biography;

    @JsonProperty(value = "sport_club_info")
    private SportClubInfoDto sportClubDto;

    private int age;
    private Sex sex;
}
