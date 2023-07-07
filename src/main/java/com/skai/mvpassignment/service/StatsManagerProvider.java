package com.skai.mvpassignment.service;

import com.skai.mvpassignment.model.statistics.PlayerStats;

import java.io.File;

public interface StatsManagerProvider {
    StatsManager<? extends PlayerStats> getStatsManager(File file);
}
