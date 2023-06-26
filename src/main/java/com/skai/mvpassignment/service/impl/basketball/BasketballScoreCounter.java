package com.skai.mvpassignment.service.impl.basketball;

import com.skai.mvpassignment.model.statistics.BasketballPlayerStats;
import com.skai.mvpassignment.model.PlayerData;
import com.skai.mvpassignment.service.AwardService;
import com.skai.mvpassignment.service.ScoreCounter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@Setter
public class BasketballScoreCounter extends ScoreCounter {
    private final AwardService<BasketballPlayerStats> awardService;
    private static final String GAME_NAME = "BASKETBALL";
    private static final String GAME_FILE_EXTENSION = "csv";

    @Value("${basketball.coefficients.scored-point}")
    private Integer scoredPointsCoefficient;
    @Value("${basketball.coefficients.rebound}")
    private Integer reboundsCoefficient;
    @Value("${basketball.coefficients.assist}")
    private Integer assistsCoefficient;

    @Autowired
    protected BasketballScoreCounter(AwardService<BasketballPlayerStats> awardService) {
        super(GAME_NAME, GAME_FILE_EXTENSION);
        this.awardService = awardService;
    }

    @Override
    public List<PlayerData> countPlayersScore(File gameFile) {
        List<BasketballPlayerStats> gamePlayers = awardService.getPlayersWithBonuses(gameFile);
        return gamePlayers.stream().map(this::addGameScore).toList();
    }

    private PlayerData addGameScore(BasketballPlayerStats playerStats){
        PlayerData playerData = playerStats.getPlayerData();
        Integer gameScore = 0;
        gameScore += playerStats.getScoredPoints() * scoredPointsCoefficient;
        gameScore += playerStats.getRebounds() * reboundsCoefficient;
        gameScore += playerStats.getAssists() * assistsCoefficient;
        playerData.setRatingPoints(playerData.getRatingPoints() + gameScore);
        return playerData;
    }
}
