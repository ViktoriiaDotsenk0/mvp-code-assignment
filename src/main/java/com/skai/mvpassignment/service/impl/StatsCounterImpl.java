package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.model.GameResult;
import com.skai.mvpassignment.model.statistics.PlayerStats;
import com.skai.mvpassignment.service.FileParser;
import com.skai.mvpassignment.service.GameFileService;
import com.skai.mvpassignment.service.StatsCounter;
import com.skai.mvpassignment.service.StatsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatsCounterImpl implements StatsCounter {
    private final FileParser fileParser;
    private final GameFileService gameFileService;
    private final Map<String, StatsManager<?>> statsManagersMap = new HashMap<>();

    @Autowired
    public StatsCounterImpl(FileParser fileParser, GameFileService gameFileService, List<StatsManager<?>> statsManagers) {
        this.fileParser = fileParser;
        this.gameFileService = gameFileService;
        statsManagers.forEach(statsManager -> statsManagersMap.put(statsManager.getGameName(), statsManager));
    }


    @Override
    public Map<String, List<GameResult>> calculateGameResults(File file) {
        String gameName = gameFileService.getGameName(file);
        StatsManager<?> statsManager = statsManagersMap.get(gameName);
        if (statsManager == null)
            throw new IllegalArgumentException("Unsupported game: " + gameName);
        return calculateGameResults(file, statsManager);
    }

    private Map<String, List<GameResult>> calculateGameResults(File file, StatsManager<? extends PlayerStats> statsManager) {
        return fileParser.getTeamsStats(file, statsManager.getType()).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(stats -> new GameResult(stats, statsManager.calculateGameScore(stats)))
                                .collect(Collectors.toList())));
    }
}
