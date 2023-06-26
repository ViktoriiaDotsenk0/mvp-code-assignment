package com.skai.mvpassignment.service;

import com.skai.mvpassignment.model.statistics.PlayerStats;

import java.io.File;
import java.util.List;

public interface AwardService<T extends PlayerStats> {
    List<T> getPlayersWithBonuses(File gameFile);
}
