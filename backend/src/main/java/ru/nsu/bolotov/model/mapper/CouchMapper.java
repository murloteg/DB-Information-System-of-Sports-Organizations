package ru.nsu.bolotov.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.nsu.bolotov.model.dto.sport.couch.CouchCreationDto;
import ru.nsu.bolotov.model.dto.sport.couch.CouchInfoDto;
import ru.nsu.bolotov.model.entity.sport.Couch;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CouchMapper {
    Couch map(CouchCreationDto couchDto);
    CouchInfoDto map(Couch couch);
}
