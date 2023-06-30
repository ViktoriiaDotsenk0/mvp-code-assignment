package com.skai.mvpassignment.service.impl.handball;

import com.skai.mvpassignment.model.statistics.HandballPlayerStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HandballStatsManagerTest {

    @Mock
    private HandballPlayerStats playerStats;

    private HandballStatsManager statsManager;

    @BeforeEach
    public void setup() {
        statsManager = new HandballStatsManager();
        statsManager.setGoalsMadeCoeff(2);
        statsManager.setGoalsReceivedCoeff(1);
    }

    @Test
    public void testCalculateGameScore() {
        // given
        when(playerStats.getGoalsMade()).thenReturn(3);
        when(playerStats.getGoalsReceived()).thenReturn(2);

        //when
        int gameScore = statsManager.calculateGameScore(playerStats);

        // then
        assertEquals(8, gameScore);
    }

}