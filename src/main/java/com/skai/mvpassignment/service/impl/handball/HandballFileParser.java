package com.skai.mvpassignment.service.impl.handball;

import com.skai.mvpassignment.exceptions.ErrorLogger;
import com.skai.mvpassignment.model.statistics.HandballPlayerStats;
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
public class HandballFileParser implements FileParser<HandballPlayerStats> {
    private final CsvParser<HandballPlayerStats> csvParser;
    private final PlayersStatsValidator playersStatsValidator;

    @Override
    public Map<String, List<HandballPlayerStats>> getTeamsStats(File gameFile) {
        Map<String, List<HandballPlayerStats>> result = new HashMap<>();
        for (HandballPlayerStats handballPlayerStats : getGamePlayersStats(gameFile)) {
            List<HandballPlayerStats> team = result.computeIfAbsent(handballPlayerStats.getTeamName(), v -> new ArrayList<>());
            team.add(handballPlayerStats);
        }
        return result;
    }

    private List<HandballPlayerStats> getGamePlayersStats(File gameFile) {
        try {
            List<HandballPlayerStats> handballPlayersStats = csvParser.parse(gameFile, HandballPlayerStats.class);
            playersStatsValidator.validate(handballPlayersStats, gameFile.getPath());
            return handballPlayersStats;
        } catch (Exception e) {
            ErrorLogger.logAndExit(e.getMessage());
        }
        return new ArrayList<>();
    }
}