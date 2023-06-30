package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.model.GameResult;
import com.skai.mvpassignment.model.statistics.BasketballPlayerStats;
import com.skai.mvpassignment.model.statistics.PlayerStats;
import com.skai.mvpassignment.service.FileParser;
import com.skai.mvpassignment.service.GameFileService;
import com.skai.mvpassignment.service.StatsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatsCounterImplTest {

    private StatsCounterImpl underTest;
    private FileParser fileParser;
    private GameFileService gameFileService;

    private final StatsManager<BasketballPlayerStats> mockStatsManager = mock(StatsManager.class);

    @BeforeEach
    void setUp() {
        fileParser = mock(FileParser.class);
        gameFileService = mock(GameFileService.class);
        List<StatsManager<?>> mockStatsManagers = new ArrayList<>();
        mockStatsManagers.add(mockStatsManager);
        when(mockStatsManager.getGameName()).thenReturn("BASKETBALL");
        underTest = new StatsCounterImpl(fileParser, gameFileService, mockStatsManagers);
    }

    @Test
    void calculateGameResults() {
        // given
        File mockFile = mock(File.class);
        PlayerStats playerStats = BasketballPlayerStats.builder().teamName("Team A").name("Player1").build();
        when(gameFileService.getGameName(mockFile)).thenReturn("BASKETBALL");
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
        verify(gameFileService, times(1)).getGameName(mockFile);
        verify(mockStatsManager, times(1)).calculateGameScore(playerStats);
        verify(fileParser, times(1)).getTeamsStats(mockFile, BasketballPlayerStats.class);
    }

    @Test
    public void testWithUnsupportedGame(){
        //given
        File mockFile = mock(File.class);
        when(gameFileService.getGameName(mockFile)).thenReturn("HANDBALL");

        //then
        assertThrows(IllegalArgumentException.class, ()-> underTest.calculateGameResults(mockFile));
    }

}