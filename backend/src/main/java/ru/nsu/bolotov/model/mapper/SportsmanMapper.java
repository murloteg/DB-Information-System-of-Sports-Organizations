package ru.nsu.bolotov.model.mapper;

import org.mapstruct.*;
import ru.nsu.bolotov.model.dto.sport.sportsman.SportsmanCreationDto;
import ru.nsu.bolotov.model.dto.sport.sportsman.SportsmanInfoDto;
import ru.nsu.bolotov.model.entity.sport.Sportsman;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SportsmanMapper {
    Sportsman map(SportsmanCreationDto sportsmanDto);
    SportsmanInfoDto map(Sportsman sportsman);
}
