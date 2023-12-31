package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.model.GameResult;
import com.skai.mvpassignment.model.PlayerData;
import com.skai.mvpassignment.service.AwardService;
import com.skai.mvpassignment.service.BonusService;
import com.skai.mvpassignment.service.StatsCounter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AwardServiceImpl implements AwardService {

    private final StatsCounter statsCounter;
    private final BonusService bonusService;

    @Override
    public List<PlayerData> getPlayersWithBonuses(File gameFile) {
        Map<String, List<GameResult>> teamsGameResults = statsCounter.calculateGameResults(gameFile);
        String winnerTeamName = getWinnerTeamName(teamsGameResults);
        return teamsGameResults.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> applyBonuses(entry, winnerTeamName)))
                .values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<PlayerData> applyBonuses(Map.Entry<String,List<GameResult>> entry, String winnerTeamName){
        return entry.getKey().equals(winnerTeamName) ?
                bonusService.addBonuses(entry.getValue()) :
                entry.getValue().stream()
                        .map(GameResult::getPlayerData)
                        .collect(Collectors.toList());
    }

    private String getWinnerTeamName(Map<String, List<GameResult>> teamsGameResults) {
        try {
            return calculateTeamScores(teamsGameResults)
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .skip(Math.max(0, teamsGameResults.size() - 2))
                    .distinct()
                    .collect(Collectors.collectingAndThen(
                            Collectors.toList(),
                            this::getWinnerTeamName
                    ))
                    .orElseThrow(() -> new IllegalStateException("Invalid game statistics. Cannot define the winner team"));
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalStateException("Invalid game statistics. Must be at least 2 teams in a game");
        }
    }

    private Map<String, Integer> calculateTeamScores(Map<String, List<GameResult>> teamsGameResults) {
        return teamsGameResults.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue()
                                .stream()
                                .mapToInt(gameResult -> gameResult.getGameData().getGameScore())
                                .sum()
                ));
    }

    private Optional<String> getWinnerTeamName(List<Map.Entry<String, Integer>> entries) {
        return entries.get(0).getValue().equals(entries.get(1).getValue())
                ? Optional.empty()
                : Optional.of(entries.get(1).getKey());
    }
}
