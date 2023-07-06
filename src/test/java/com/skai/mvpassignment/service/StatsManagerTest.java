package com.skai.mvpassignment.service;

import com.skai.mvpassignment.model.statistics.BasketballPlayerStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class StatsManagerTest {
    private StatsCalculator<BasketballPlayerStats> mockCalculator;
    private StatsManager<BasketballPlayerStats> underTest;
    @BeforeEach
    public void setUp() {
        mockCalculator = mock(StatsCalculator.class);
        underTest = new StatsManager<>("BASKETBALL", BasketballPlayerStats.class, mockCalculator);
    }

    @Test
    public void testCalculateGameScore() {
        // given
        BasketballPlayerStats basketballPlayerStats = mock(BasketballPlayerStats.class);

        //when
        underTest.calculateGameScore(basketballPlayerStats);

        // then
        verify(mockCalculator, times(1)).calculateGameScore(basketballPlayerStats);
    }
}