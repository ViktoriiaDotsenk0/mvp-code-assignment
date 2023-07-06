package com.skai.mvpassignment.service;

import com.skai.mvpassignment.model.statistics.PlayerStats;

public record StatsManager<T extends PlayerStats>(String gameName, Class<T> type, StatsCalculator<T> calculator) {
    public Integer calculateGameScore(PlayerStats playerStats) {
        return calculator.calculateGameScore(playerStats);
    }
}
