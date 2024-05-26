package ru.nsu.bolotov.model.mapper.facility;

import org.mapstruct.*;
import ru.nsu.bolotov.model.dto.facility.hall.SportHallCreationDto;
import ru.nsu.bolotov.model.dto.facility.hall.SportHallInfoDto;
import ru.nsu.bolotov.model.entity.facility.SportHall;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SportHallMapper {
    @Mapping(source = "sportFacilityCreationDto.facilityName", target = "facilityName")
    @Mapping(source = "sportFacilityCreationDto.facilityType", target = "facilityType")
    SportHall map(SportHallCreationDto sportHallCreationDto);

    @Mapping(source = "facilityName", target = "sportFacilityInfoDto.facilityName")
    @Mapping(source = "facilityType", target = "sportFacilityInfoDto.facilityType")
    SportHallInfoDto map(SportHall sportHall);
}
