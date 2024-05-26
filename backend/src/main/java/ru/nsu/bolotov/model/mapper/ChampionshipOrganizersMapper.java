package ru.nsu.bolotov.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.nsu.bolotov.model.dto.championship.organizer.ChampionshipOrganizerCreationDto;
import ru.nsu.bolotov.model.dto.championship.organizer.ChampionshipOrganizerInfoDto;
import ru.nsu.bolotov.model.entity.championship.ChampionshipOrganizer;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ChampionshipOrganizersMapper {
    ChampionshipOrganizer map(ChampionshipOrganizerCreationDto championshipOrganizerCreationDto);
    ChampionshipOrganizerInfoDto map(ChampionshipOrganizer championshipOrganizer);
}
