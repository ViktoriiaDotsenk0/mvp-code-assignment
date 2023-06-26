package com.skai.mvpassignment.service.impl.basketball;

import com.skai.mvpassignment.exceptions.ErrorLogger;
import com.skai.mvpassignment.model.statistics.BasketballPlayerStats;
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
public class BasketballAwardService implements AwardService<BasketballPlayerStats> {
    private final FileParser<BasketballPlayerStats> fileParser;
    private final BonusService bonusService;

    @Override
    public List<BasketballPlayerStats> getPlayersWithBonuses(File gameFile) {
        Map<String, List<BasketballPlayerStats>> teamsPlayerStats = fileParser.getTeamsStats(gameFile);
        String winnerTeamName = getWinnerTeamName(teamsPlayerStats);
        bonusService.addWinnersBonuses(teamsPlayerStats.get(winnerTeamName));
        return teamsPlayerStats.values().stream().flatMap(List::stream).toList();
    }


    private String getWinnerTeamName(Map<String, List<BasketballPlayerStats>> teamsPlayerStats) {
        Map<String, List<String>> goalsTeamMap = new HashMap<>();
        int maxGoalsCount = 0;
        for (Map.Entry<String, List<BasketballPlayerStats>> entry : teamsPlayerStats.entrySet()) {
            int maxScoredPoints = 0;
            for (BasketballPlayerStats basketballPlayerStats : entry.getValue()) {
                maxScoredPoints += basketballPlayerStats.getScoredPoints();
            }
            if (maxGoalsCount <= maxScoredPoints) {
                maxGoalsCount = maxScoredPoints;
                goalsTeamMap.computeIfAbsent(Integer.toString(maxScoredPoints), k -> new ArrayList<>());
                goalsTeamMap.get(Integer.toString(maxScoredPoints)).add(entry.getKey());
            }
        }
        List<String> winnerTeamList = goalsTeamMap.get(String.valueOf(maxGoalsCount));
        if (winnerTeamList == null || winnerTeamList.size() > 1)
            ErrorLogger.logAndExit("Failed to determine basketball winner team");
        else return winnerTeamList.get(0);
        return null;
    }

}
