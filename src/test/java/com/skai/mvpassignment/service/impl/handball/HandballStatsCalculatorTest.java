package com.skai.mvpassignment.service.impl.handball;

import com.skai.mvpassignment.model.statistics.HandballPlayerStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(properties={
        "handball.coefficients.goals-made=1",
        "handball.coefficients.goals-received=-1"})
class HandballStatsCalculatorTest {

    @Mock
    private HandballPlayerStats playerStats;

    @Autowired
    private HandballStatsCalculator statsManager;

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