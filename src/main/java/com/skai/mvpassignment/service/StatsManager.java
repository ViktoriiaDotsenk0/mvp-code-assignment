package com.skai.mvpassignment.service;

import com.skai.mvpassignment.model.statistics.PlayerStats;

public interface StatsManager<T extends PlayerStats> {
     String getGameName();
     Class<T> getType();
     StatsCalculator<T> getStatsCalculator();
    default Integer calculateGameScore(PlayerStats playerStats){
        return getStatsCalculator().calculateGameScore(playerStats);
    }
}
