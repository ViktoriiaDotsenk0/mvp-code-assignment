package com.skai.mvpassignment.service.impl.basketball;

import com.skai.mvpassignment.model.statistics.BasketballPlayerStats;
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
        "basketball.coefficients.scored-point=2",
        "basketball.coefficients.rebound=1",
        "basketball.coefficients.assist=1"})
class BasketballStatsCalculatorTest {

    @Mock
    private BasketballPlayerStats playerStats;

    @Autowired
    private BasketballStatsCalculator statsCalculator;



    @Test
    public void testCalculateGameScore() {
        // given
        when(playerStats.getScoredPoints()).thenReturn(10);
        when(playerStats.getRebounds()).thenReturn(5);
        when(playerStats.getAssists()).thenReturn(2);

        // when
        int gameScore = statsCalculator.calculateGameScore(playerStats);

        // then
        assertEquals(27, gameScore);
    }
}