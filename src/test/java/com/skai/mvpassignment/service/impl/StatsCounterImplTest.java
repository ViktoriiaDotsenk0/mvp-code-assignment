package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.model.GameResult;
import com.skai.mvpassignment.model.statistics.BasketballPlayerStats;
import com.skai.mvpassignment.model.statistics.PlayerStats;
import com.skai.mvpassignment.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatsCounterImplTest {

    @InjectMocks
    private StatsCounterImpl underTest;
    @Mock
    private FileParser fileParser;

    @Mock
    private  StatsManagerProvider mockStatsManagerProvider;

    @Mock
    private StatsManager mockStatsManager;

    @Test
    void calculateGameResults() {
        // given
        File mockFile = mock(File.class);
        when(mockStatsManagerProvider.getStatsManager(mockFile)).thenReturn(mockStatsManager);
        PlayerStats playerStats = BasketballPlayerStats.builder().teamName("Team A").name("Player1").build();
        when(mockStatsManager.calculateGameScore(playerStats)).thenReturn(100);
        when(mockStatsManager.getType()).thenReturn(BasketballPlayerStats.class);
        when(fileParser.getTeamsStats(mockFile, BasketballPlayerStats.class)).thenReturn(Collections.singletonMap("Team A", Collections.singletonList(playerStats)));

        // when
        Map<String, List<GameResult>> gameResults = underTest.calculateGameResults(mockFile);

        // then
        assertEquals(1, gameResults.size());
        assertTrue(gameResults.containsKey("Team A"));
        List<GameResult> teamResults = gameResults.get("Team A");
        assertEquals(1, teamResults.size());
        GameResult gameResult = teamResults.get(0);
        assertEquals(playerStats.getName(), gameResult.getPlayerData().getName());
        assertEquals(100, gameResult.getPlayerData().getRatingPoints());
        verify(mockStatsManager, times(1)).calculateGameScore(playerStats);
        verify(fileParser, times(1)).getTeamsStats(mockFile, BasketballPlayerStats.class);
    }
}