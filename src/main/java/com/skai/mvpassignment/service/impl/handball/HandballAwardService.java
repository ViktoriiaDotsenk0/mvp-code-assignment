package com.skai.mvpassignment.service.impl.handball;

import com.skai.mvpassignment.exceptions.ErrorLogger;
import com.skai.mvpassignment.model.statistics.HandballPlayerStats;
import com.skai.mvpassignment.service.AwardService;
import com.skai.mvpassignment.service.BonusService;
import com.skai.mvpassignment.service.FileParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HandballAwardService implements AwardService<HandballPlayerStats> {
    private final FileParser<HandballPlayerStats> fileParser;
    private final BonusService bonusService;

    @Override
    public List<HandballPlayerStats> getPlayersWithBonuses(File gameFile) {
        Map<String, List<HandballPlayerStats>> teamsPlayerStats = fileParser.getTeamsStats(gameFile);
        String winnerTeamName = getWinnerTeamName(teamsPlayerStats);
        bonusService.addWinnersBonuses(teamsPlayerStats.get(winnerTeamName));
        return teamsPlayerStats.values().stream().flatMap(List::stream).toList();
    }

    private String getWinnerTeamName(Map<String, List<HandballPlayerStats>> teamsPlayerStats) {
        Map<String, List<String>> goalsTeamMap = new HashMap<>();
        int maxGoalsCount = 0;
        for (Map.Entry<String, List<HandballPlayerStats>> entry : teamsPlayerStats.entrySet()) {
            Integer goalsCount = 0;
            for (HandballPlayerStats handballPlayerStats : entry.getValue()) {
                goalsCount += handballPlayerStats.getGoalsMade();
            }
            if (maxGoalsCount <= goalsCount) {
                maxGoalsCount = goalsCount;
                goalsTeamMap.computeIfAbsent(goalsCount.toString(), k -> new ArrayList<>());
                goalsTeamMap.get(goalsCount.toString()).add(entry.getKey());
            }
        }
        List<String> winnerTeamList = goalsTeamMap.get(String.valueOf(maxGoalsCount));
        if (winnerTeamList == null || winnerTeamList.size() > 1)
            ErrorLogger.logAndExit("Failed to determine handball winner team");
        else return winnerTeamList.get(0);
        return null;
    }
}
