package com.skai.mvpassignment.service.impl.handball;

import com.skai.mvpassignment.model.statistics.HandballPlayerStats;
import com.skai.mvpassignment.model.PlayerData;
import com.skai.mvpassignment.service.ScoreCounter;
import com.skai.mvpassignment.service.AwardService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@Setter
public class HandballScoreCounter extends ScoreCounter {
    @Value("${handball.coefficients.goals-made}")
    private Integer goalsMadeCoefficient;
    @Value("${handball.coefficients.goals-received}")
    private Integer goalsReceivedCoefficient;

    private static final String GAME_NAME = "HANDBALL";
    private static final String GAME_FILE_EXTENSION = "csv";
    private final AwardService<HandballPlayerStats> awardService;

    @Autowired
    protected HandballScoreCounter(AwardService<HandballPlayerStats> awardService) {
        super(GAME_NAME, GAME_FILE_EXTENSION);
        this.awardService = awardService;
    }

    @Override
    public List<PlayerData> countPlayersScore(File gameFile) {
        List<HandballPlayerStats> gamePlayers = awardService.getPlayersWithBonuses(gameFile);
        return gamePlayers.stream().map(this::addGameScore).toList();
    }

    private PlayerData addGameScore(HandballPlayerStats playerStats) {
        PlayerData playerData = playerStats.getPlayerData();
        Integer gameScore = 0;
        gameScore += playerStats.getGoalsMade() * goalsMadeCoefficient;
        gameScore += playerStats.getGoalsReceived() * goalsReceivedCoefficient;
        playerData.setRatingPoints(playerData.getRatingPoints() + gameScore);
        return playerData;
    }
}
