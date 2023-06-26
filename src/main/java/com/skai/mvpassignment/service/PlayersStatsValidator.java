package com.skai.mvpassignment.service;

import com.skai.mvpassignment.exceptions.ValidationException;
import com.skai.mvpassignment.model.statistics.PlayerStats;

import java.util.List;

public interface PlayersStatsValidator {
    void validate(List<? extends PlayerStats> playersStats, String path) throws ValidationException;
}
