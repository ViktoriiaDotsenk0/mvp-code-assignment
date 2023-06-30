package com.skai.mvpassignment.model;

import com.skai.mvpassignment.model.statistics.PlayerStats;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class GameResult {
    private PlayerData playerData;
    private GameData gameData;

    public GameResult(PlayerStats playerStats, Integer gameScore){
        this.gameData = GameData.builder()
                .gameScore(gameScore)
                .teamName(playerStats.getTeamName())
                .build();
        this.playerData = PlayerData.builder()
                .name(playerStats.getName())
                .nick(playerStats.getNick())
                .number(playerStats.getNumber())
                .ratingPoints(gameScore)
                .build();
    }
}
