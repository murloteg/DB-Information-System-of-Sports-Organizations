package ru.nsu.bolotov.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.bolotov.model.dto.sport.couch.CouchOverviewDto;
import ru.nsu.bolotov.model.entity.sport.Couch;
import ru.nsu.bolotov.model.entity.sport.TotalSportsmanInfo;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class CouchOverviewDtoMapper implements Function<TotalSportsmanInfo, CouchOverviewDto> {
    @Override
    public CouchOverviewDto apply(TotalSportsmanInfo totalSportsmanInfo) {
        Couch couch = totalSportsmanInfo.getCouch();
        return new CouchOverviewDto(
                couch.getCouchId(),
                couch.getFirstName(),
                couch.getLastName(),
                couch.getAge(),
                couch.getSex(),
                totalSportsmanInfo.getSportType().getSportTypeId()
        );
    }
}
