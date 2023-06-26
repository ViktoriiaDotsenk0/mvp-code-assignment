package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.model.PlayerData;
import com.skai.mvpassignment.model.statistics.PlayerStats;
import com.skai.mvpassignment.service.BonusService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Setter
public class BonusServiceImpl implements BonusService {
    @Value("${game.winner.bonus}")
    private Integer bonuses;

    @Override
    public void addWinnersBonuses(List<? extends PlayerStats> winners) {
        winners.forEach(winner -> {
            PlayerData playerData = winner.getPlayerData();
            playerData.setRatingPoints(playerData.getRatingPoints() + bonuses);
        });
    }

}
