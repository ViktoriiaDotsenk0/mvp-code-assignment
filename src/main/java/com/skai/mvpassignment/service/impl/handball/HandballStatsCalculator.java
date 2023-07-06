package com.skai.mvpassignment.service.impl.handball;

import com.skai.mvpassignment.model.statistics.HandballPlayerStats;
import com.skai.mvpassignment.model.statistics.PlayerStats;
import com.skai.mvpassignment.service.StatsCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HandballStatsCalculator implements StatsCalculator<HandballPlayerStats> {
    @Value("${handball.coefficients.goals-made}")
    private Integer goalsMadeCoeff;
    @Value("${handball.coefficients.goals-received}")
    private Integer goalsReceivedCoeff;

    @Override
    public Integer calculateGameScore(PlayerStats playersStats) {
        HandballPlayerStats handballPlayerStats = (HandballPlayerStats) playersStats;
        int gameScore = handballPlayerStats.getGoalsMade() * goalsMadeCoeff;
        gameScore += handballPlayerStats.getGoalsReceived() * goalsReceivedCoeff;
        return gameScore;
    }
}
