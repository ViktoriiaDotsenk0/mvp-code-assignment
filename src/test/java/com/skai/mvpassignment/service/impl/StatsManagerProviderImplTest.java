package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.model.statistics.BasketballPlayerStats;
import com.skai.mvpassignment.model.statistics.PlayerStats;
import com.skai.mvpassignment.service.GameFileService;
import com.skai.mvpassignment.service.StatsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StatsManagerProviderImplTest {

    @Mock
    private GameFileService gameFileService;

    private StatsManagerProviderImpl statsManagerProvider;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        statsManagerProvider = new StatsManagerProviderImpl(gameFileService, createStatsManagers());
    }

    @Test
    public void testGetStatsManager() {
        File file = mock(File.class);
        String gameName = "BASKETBALL";

        when(gameFileService.getGameName(file)).thenReturn(gameName);

        StatsManager<?> statsManager = statsManagerProvider.getStatsManager(file);

        assertNotNull(statsManager);
        assertEquals(gameName, statsManager.getGameName());
    }

    @Test
    public void testGetStatsManagerUnsupportedGame() {
        File file = mock(File.class);
        String gameName = "FOOTBALL";

        when(gameFileService.getGameName(file)).thenReturn(gameName);

        assertThrows(IllegalArgumentException.class, () -> statsManagerProvider.getStatsManager(file));
    }

    private List<StatsManager<? extends PlayerStats>> createStatsManagers() {
        StatsManager<BasketballPlayerStats> basketballStatsManager = mock(StatsManager.class);
        when(basketballStatsManager.getGameName()).thenReturn("BASKETBALL");
        return List.of(basketballStatsManager);
    }
}
