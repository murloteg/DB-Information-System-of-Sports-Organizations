package ru.nsu.bolotov.model.mapper;

import org.mapstruct.*;
import ru.nsu.bolotov.model.dto.championship.ChampionshipGeneralInfoDto;
import ru.nsu.bolotov.model.dto.championship.ChampionshipValidatedCreationDto;
import ru.nsu.bolotov.model.entity.championship.Championship;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ChampionshipMapper {
    Championship map(ChampionshipValidatedCreationDto championshipCreationDto);

    @Mapping(source = "sportFacility.facilityName", target = "sportFacilityInfoDto.facilityName")
    @Mapping(source = "sportFacility.facilityType", target = "sportFacilityInfoDto.facilityType")
    @Mapping(source = "sportFacility.facilityId", target = "sportFacilityInfoDto.facilityId")
    ChampionshipGeneralInfoDto map(Championship championship);
}
