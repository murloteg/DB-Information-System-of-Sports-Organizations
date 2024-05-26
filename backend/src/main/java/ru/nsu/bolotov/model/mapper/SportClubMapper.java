package ru.nsu.bolotov.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.nsu.bolotov.model.dto.sport.club.SportClubDto;
import ru.nsu.bolotov.model.entity.sport.SportClub;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SportClubMapper {
    SportClub map(SportClubDto sportClubDto);
    SportClubDto map(SportClub sportClub);
}
