package com.skai.mvpassignment.service.impl.handball;

import com.skai.mvpassignment.model.GameData;
import com.skai.mvpassignment.model.GameResult;
import com.skai.mvpassignment.model.PlayerData;
import com.skai.mvpassignment.model.statistics.HandballPlayerStats;
import com.skai.mvpassignment.model.statistics.PlayerStats;
import com.skai.mvpassignment.service.StatsManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HandballStatsManager extends StatsManager<HandballPlayerStats> {

    @Value("${handball.coefficients.goals-made}")
    private Integer goalsMadeCoeff;
    @Value("${handball.coefficients.goals-received}")
    private Integer goalsReceivedCoeff;

    public HandballStatsManager() {
        super(HandballPlayerStats.class, "HANDBALL");
    }

    @Override
    public Integer calculateGameScore(PlayerStats playersStats) {
        HandballPlayerStats handballPlayerStats = (HandballPlayerStats) playersStats;
        int gameScore = handballPlayerStats.getGoalsMade() * goalsMadeCoeff;
        gameScore += handballPlayerStats.getGoalsReceived() * goalsReceivedCoeff;
        return gameScore;
    }
}
