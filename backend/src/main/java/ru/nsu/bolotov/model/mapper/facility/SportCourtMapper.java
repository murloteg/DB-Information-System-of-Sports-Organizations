package ru.nsu.bolotov.model.mapper.facility;

import org.mapstruct.*;
import ru.nsu.bolotov.model.dto.facility.SportCourtCreationDto;
import ru.nsu.bolotov.model.dto.facility.SportCourtInfoDto;
import ru.nsu.bolotov.model.entity.facility.SportCourt;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SportCourtMapper {
    @Mapping(source = "sportFacilityCreationDto.facilityName", target = "facilityName")
    @Mapping(source = "sportFacilityCreationDto.facilityType", target = "facilityType")
    SportCourt map(SportCourtCreationDto sportCourtCreationDto);

    @Mapping(source = "facilityName", target = "sportFacilityInfoDto.facilityName")
    @Mapping(source = "facilityType", target = "sportFacilityInfoDto.facilityType")
    SportCourtInfoDto map(SportCourt sportCourt);
}
