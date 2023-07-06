package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.service.GameFileService;
import com.skai.mvpassignment.service.StatsManager;
import com.skai.mvpassignment.service.StatsManagerProvider;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatsManagerProviderImpl implements StatsManagerProvider {
    private final GameFileService gameFileService;
    private final Map<String, StatsManager<?>> statsManagersMap;

    public StatsManagerProviderImpl(GameFileService gameFileService, List<StatsManager<?>> statsManagers) {
        this.gameFileService = gameFileService;
        this.statsManagersMap = statsManagers.stream().collect(Collectors.toMap(
                StatsManager::gameName,
                statsManager -> statsManager
        ));
    }

    @Override
    public StatsManager<?> getStatsManager(File file) {
        String gameName = gameFileService.getGameName(file);
        StatsManager<?> statsManager = statsManagersMap.get(gameName);
        if (statsManager == null)
            throw new IllegalArgumentException("Unsupported game: " + gameName);
        return statsManager;
    }
}
