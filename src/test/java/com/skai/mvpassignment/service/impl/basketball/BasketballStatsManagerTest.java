package com.skai.mvpassignment.service.impl.basketball;

import com.skai.mvpassignment.model.statistics.BasketballPlayerStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasketballStatsManagerTest {

    @Mock
    private BasketballPlayerStats playerStats;

    @Mock
    private BasketballStatsManager statsManager;

    @BeforeEach
    public void setup() {
        statsManager = new BasketballStatsManager();
        statsManager.setScoredPointsCoeff(2);
        statsManager.setReboundsCoeff(1);
        statsManager.setAssistsCoeff(1);
    }


    @Test
    public void testCalculateGameScore() {
        // given
        when(playerStats.getScoredPoints()).thenReturn(10);
        when(playerStats.getRebounds()).thenReturn(5);
        when(playerStats.getAssists()).thenReturn(2);

        // when
        int gameScore = statsManager.calculateGameScore(playerStats);

        // then
        assertEquals(27, gameScore);
    }
}