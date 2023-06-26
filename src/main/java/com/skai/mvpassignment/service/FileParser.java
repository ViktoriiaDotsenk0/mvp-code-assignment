package com.skai.mvpassignment.service;

import com.skai.mvpassignment.model.statistics.PlayerStats;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface FileParser<T extends  PlayerStats> {
    Map<String, List<T>> getTeamsStats(File gameFile);
}
