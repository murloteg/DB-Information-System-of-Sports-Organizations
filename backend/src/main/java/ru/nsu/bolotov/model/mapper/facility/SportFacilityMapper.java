package ru.nsu.bolotov.model.mapper.facility;

import org.mapstruct.*;
import ru.nsu.bolotov.model.dto.facility.GeneralSportFacilityInfoDto;
import ru.nsu.bolotov.model.entity.facility.SportFacility;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SportFacilityMapper {
    @Mapping(source = "facilityId", target = "facilityId")
    @Mapping(source = "facilityName", target = "facilityName")
    @Mapping(source = "facilityType", target = "facilityType")
    GeneralSportFacilityInfoDto map(SportFacility sportFacility);
}
