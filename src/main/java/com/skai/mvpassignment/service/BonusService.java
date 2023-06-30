package com.skai.mvpassignment.service;

import com.skai.mvpassignment.model.GameResult;
import com.skai.mvpassignment.model.statistics.PlayerStats;

import java.util.List;
import java.util.Map;

public interface BonusService {
    List<GameResult> addBonuses(List<GameResult> winners);
}