package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.model.statistics.PlayerStats;
import com.skai.mvpassignment.service.FileParser;
import com.skai.mvpassignment.service.PlayersStatsValidator;
import com.skai.mvpassignment.util.CsvParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileParserImpl implements FileParser {
    private final CsvParser<PlayerStats> csvParser;
    private final PlayersStatsValidator playersStatsValidator;

    @Override
    public Map<String, List<PlayerStats>> getTeamsStats(File gameFile, Class<? extends PlayerStats> type) {
        Map<String, List<PlayerStats>> result = new HashMap<>();
        for (PlayerStats playerStats : getGamePlayersStats(gameFile, type)) {
            List<PlayerStats> team = result.computeIfAbsent(playerStats.getTeamName(), v -> new ArrayList<>());
            team.add(playerStats);
        }
        return result;
    }

    private List<? extends PlayerStats> getGamePlayersStats(File gameFile, Class<? extends PlayerStats> type) {
        try {
            List<PlayerStats> playersStats = csvParser.parse(gameFile, type);
            playersStatsValidator.validate(playersStats, gameFile.getPath());
            return playersStats;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
