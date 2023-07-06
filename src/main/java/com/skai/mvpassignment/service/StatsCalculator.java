package com.skai.mvpassignment.service;

import com.skai.mvpassignment.model.statistics.PlayerStats;

public interface StatsCalculator<T extends PlayerStats> {
    Integer calculateGameScore(PlayerStats playersStats);

}
