package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.model.statistics.PlayerStats;
import com.skai.mvpassignment.service.FileParser;
import com.skai.mvpassignment.service.PlayersStatsValidator;
import com.skai.mvpassignment.util.CsvParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileParserImpl implements FileParser {
    private final CsvParser<PlayerStats> csvParser;
    private final PlayersStatsValidator playersStatsValidator;

    @Override
    public Map<String, List<PlayerStats>> getTeamsStats(File gameFile, Class<? extends PlayerStats> type) {
        return getGamePlayersStats(gameFile, type)
                .stream()
                .collect(Collectors.groupingBy(PlayerStats::getTeamName));
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
