package ru.nsu.bolotov.service.sport;

import org.springframework.stereotype.Service;
import ru.nsu.bolotov.model.dto.sport.rank.SportRankLevelDto;
import ru.nsu.bolotov.model.enumeration.SportRankLevel;

import java.util.ArrayList;
import java.util.List;

@Service
public class SportRankLevelService {
    public List<SportRankLevelDto> getSportRankLevels() {
        List<SportRankLevelDto> resultList = new ArrayList<>();
        for (SportRankLevel sportRankLevel : SportRankLevel.values()) {
            resultList.add(new SportRankLevelDto(sportRankLevel.name()));
        }
        return resultList;
    }
}
