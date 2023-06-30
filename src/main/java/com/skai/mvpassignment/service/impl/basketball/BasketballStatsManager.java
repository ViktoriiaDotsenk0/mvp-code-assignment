package com.skai.mvpassignment.service.impl.basketball;

import com.skai.mvpassignment.model.statistics.BasketballPlayerStats;
import com.skai.mvpassignment.model.statistics.PlayerStats;
import com.skai.mvpassignment.service.StatsManager;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Setter
public class BasketballStatsManager extends StatsManager<BasketballPlayerStats> {
    @Value("${basketball.coefficients.scored-point}")
    private Integer scoredPointsCoeff;
    @Value("${basketball.coefficients.rebound}")
    private Integer reboundsCoeff;
    @Value("${basketball.coefficients.assist}")
    private Integer assistsCoeff;


    public BasketballStatsManager() {
        super(BasketballPlayerStats.class, "BASKETBALL");
    }

    @Override
    public Integer calculateGameScore(PlayerStats playersStats) {
        BasketballPlayerStats basketballPlayerStats = (BasketballPlayerStats) playersStats;
        int gameScore = basketballPlayerStats.getScoredPoints() * scoredPointsCoeff;
        gameScore += basketballPlayerStats.getRebounds() * reboundsCoeff;
        gameScore += basketballPlayerStats.getAssists() * assistsCoeff;
        return gameScore;
    }
}
