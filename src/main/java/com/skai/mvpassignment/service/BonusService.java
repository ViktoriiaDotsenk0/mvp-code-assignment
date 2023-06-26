package com.skai.mvpassignment.service;

import com.skai.mvpassignment.model.statistics.PlayerStats;

import java.util.List;

public interface BonusService {
    void addWinnersBonuses(List<? extends PlayerStats> winners);
}
