package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.model.GameData;
import com.skai.mvpassignment.model.GameResult;
import com.skai.mvpassignment.model.PlayerData;
import com.skai.mvpassignment.service.BonusService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BonusServiceImpl implements BonusService {
    @Value("${game.winner.bonus}")
    private Integer bonuses;

    @Override
    public List<PlayerData> addBonuses(List<GameResult> winners) {
        return winners.stream()
                .map(this::addBonuses)
                .collect(Collectors.toList());
    }
    private PlayerData addBonuses(GameResult winner){
        PlayerData clonedPlayerData = new PlayerData(winner.getPlayerData());
        clonedPlayerData.setRatingPoints(clonedPlayerData.getRatingPoints() + bonuses);
        return clonedPlayerData;
    }
}
