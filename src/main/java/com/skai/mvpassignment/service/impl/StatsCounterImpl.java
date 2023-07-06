package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.model.GameResult;
import com.skai.mvpassignment.model.statistics.PlayerStats;
import com.skai.mvpassignment.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsCounterImpl implements StatsCounter {
    private final FileParser fileParser;
    private final StatsManagerProvider statsManagerProvider;

    @Override
    public Map<String, List<GameResult>> calculateGameResults(File file) {
        StatsManager<?> statsManager = statsManagerProvider.getStatsManager(file);
        return calculateGameResults(file, statsManager);
    }

    private Map<String, List<GameResult>> calculateGameResults(File file, StatsManager<? extends PlayerStats> statsManager) {
        return fileParser.getTeamsStats(file, statsManager.type()).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> calculateTeamResults(entry.getValue(), statsManager)));
    }
    private List<GameResult> calculateTeamResults(List<? extends PlayerStats> teamStats, StatsManager<? extends PlayerStats> statsManager) {
        return teamStats.stream()
                .map(stats -> new GameResult(stats, statsManager.calculateGameScore(stats)))
                .collect(Collectors.toList());
    }
}
