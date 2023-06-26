package com.skai.mvpassignment.service.impl.basketball;

import com.skai.mvpassignment.exceptions.ErrorLogger;
import com.skai.mvpassignment.model.statistics.BasketballPlayerStats;
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
public class BasketballFileParser implements FileParser<BasketballPlayerStats> {
    private final CsvParser<BasketballPlayerStats> csvParser;
    private final PlayersStatsValidator playersStatsValidator;

    @Override
    public Map<String, List<BasketballPlayerStats>> getTeamsStats(File gameFile) {
        Map<String, List<BasketballPlayerStats>> result = new HashMap<>();
        for (BasketballPlayerStats basketballPlayerStats : getGamePlayersStats(gameFile)) {
            List<BasketballPlayerStats> team = result.computeIfAbsent(basketballPlayerStats.getTeamName(), v -> new ArrayList<>());
            team.add(basketballPlayerStats);
        }
        return result;
    }

    private List<BasketballPlayerStats> getGamePlayersStats(File gameFile) {
        try {
            List<BasketballPlayerStats> basketballPlayerStats = csvParser.parse(gameFile, BasketballPlayerStats.class);
            playersStatsValidator.validate(basketballPlayerStats, gameFile.getPath());
            return basketballPlayerStats;
        } catch (Exception e) {
            ErrorLogger.logAndExit(e.getMessage());
        }
        return new ArrayList<>();
    }
}
