package com.skai.mvpassignment.service;

import com.skai.mvpassignment.model.GameResult;
import com.skai.mvpassignment.model.statistics.PlayerStats;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class StatsManager<T extends PlayerStats> {
    private Class<T> type;
    private String gameName;

    public abstract Integer calculateGameScore(PlayerStats playersStats);
}
