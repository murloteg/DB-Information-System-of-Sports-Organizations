package ru.nsu.bolotov.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.nsu.bolotov.model.dto.sport.type.SportTypeCreationDto;
import ru.nsu.bolotov.model.dto.sport.type.SportTypeInfoDto;
import ru.nsu.bolotov.model.entity.sport.SportType;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SportTypeMapper {
    SportType map(SportTypeCreationDto sportTypeDto);
    SportTypeInfoDto map(SportType sportType);
}
