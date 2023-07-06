package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.model.GameResult;
import com.skai.mvpassignment.model.PlayerData;
import com.skai.mvpassignment.model.statistics.BasketballPlayerStats;
import com.skai.mvpassignment.service.BonusService;
import com.skai.mvpassignment.service.StatsCounter;
import org.apache.commons.text.WordUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AwardServiceImplTest {

    @InjectMocks
    private AwardServiceImpl underTest;

    @Mock
    private StatsCounter mockStatsCounter;

    @Mock
    private BonusService mockBonusService;

    @Test
    public void testWithCorrectData() {
        //given
        File mockGameFile = Mockito.mock(File.class);
        GameResult loser = new GameResult(BasketballPlayerStats.builder().nick("nick1").build(), 10);
        GameResult winner = new GameResult(BasketballPlayerStats.builder().nick("nick3").build(), 20);

        List<GameResult> winners = List.of(winner);

        Map<String, List<GameResult>> teamGameResults = Map.of(
                "Team A", List.of(loser),
                "Team B", winners);

        Mockito.when(mockStatsCounter.calculateGameResults(mockGameFile)).thenReturn(teamGameResults);
        Mockito.when(mockBonusService.addBonuses(winners)).thenReturn(List.of(winner.getPlayerData()));

        //when
        List<PlayerData> result = underTest.getPlayersWithBonuses(mockGameFile);

        //then
        Mockito.verify(mockBonusService, Mockito.times(1))
                .addBonuses(Mockito.any());
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testWithIncorrectData() {
        //given
        File fileWithSimilarScore = Mockito.mock(File.class);
        File fileWithOneTeam = Mockito.mock(File.class);
        List<GameResult> losers = List.of(new GameResult(BasketballPlayerStats.builder().nick("nick1").build(), 10));
        List<GameResult> winners = List.of(new GameResult(BasketballPlayerStats.builder().nick("nick3").build(), 10));
        Map<String, List<GameResult>> teamsWithSimilarScore = Map.of(
                "Team A", losers,
                "Team B", winners);
        Mockito.when(mockStatsCounter.calculateGameResults(fileWithSimilarScore)).thenReturn(teamsWithSimilarScore);
        Mockito.when(mockStatsCounter.calculateGameResults(fileWithOneTeam)).thenReturn(Map.of("Team", winners));

        //then
        Assertions.assertThrows(IllegalStateException.class, () -> underTest.getPlayersWithBonuses(fileWithSimilarScore));
        Assertions.assertThrows(IllegalStateException.class, () -> underTest.getPlayersWithBonuses(fileWithOneTeam));
    }
}