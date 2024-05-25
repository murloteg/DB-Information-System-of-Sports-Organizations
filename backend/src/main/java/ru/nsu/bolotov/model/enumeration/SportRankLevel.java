package ru.nsu.bolotov.model.enumeration;

import lombok.Getter;

@Getter
public enum SportRankLevel {
    THIRD_YOUTH_CATEGORY(1),
    SECOND_YOUTH_CATEGORY(2),
    FIRST_YOUTH_CATEGORY(3),
    THIRD_SPORTS_CATEGORY(4),
    SECOND_SPORTS_CATEGORY(5),
    FIRST_SPORTS_CATEGORY(6),
    CANDIDATE_FOR_THE_MASTER_OF_SPORTS(7),
    MASTER_OF_SPORTS(8);

    private final int level;

    SportRankLevel(int level) {
        this.level = level;
    }
}
