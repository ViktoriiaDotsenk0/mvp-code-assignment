package com.skai.mvpassignment.service;

import com.skai.mvpassignment.model.statistics.PlayerStats;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface FileParser {
    Map<String, List<PlayerStats>> getTeamsStats(File gameFile, Class<? extends PlayerStats> type);
}
