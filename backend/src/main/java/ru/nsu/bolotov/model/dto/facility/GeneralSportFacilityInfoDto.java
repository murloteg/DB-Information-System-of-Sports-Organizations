package ru.nsu.bolotov.model.dto.facility;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GeneralSportFacilityInfoDto {
    @JsonProperty(value = "facility_id")
    private long facilityId;

    @JsonProperty(value = "facility_type")
    private String facilityType;

    @JsonProperty(value = "facility_name")
    private String facilityName;
}
