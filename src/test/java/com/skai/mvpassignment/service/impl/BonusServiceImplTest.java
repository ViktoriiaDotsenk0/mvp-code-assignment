package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.model.GameResult;
import com.skai.mvpassignment.model.PlayerData;
import com.skai.mvpassignment.model.statistics.BasketballPlayerStats;
import com.skai.mvpassignment.model.statistics.HandballPlayerStats;
import com.skai.mvpassignment.model.statistics.PlayerStats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class BonusServiceImplTest {

    private BonusServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new BonusServiceImpl();
    }

    @Test
    public void testAddBonuses() {
        // Given
        BasketballPlayerStats player1 = new BasketballPlayerStats("name1", "nick1", "12", "Team A", 10, 12, 8);
        HandballPlayerStats player2 = new HandballPlayerStats("name2", "nick2", "13", "Team B", 12, 0);
        List<GameResult> winners = Arrays.asList(new GameResult(player1,0), new GameResult(player2,0));
        int bonus = 10;
        underTest.setBonuses(bonus);
        // When
        List<GameResult> winnersWithBonuses = underTest.addBonuses(winners);

        // Then
        for (GameResult winner : winnersWithBonuses) {
            PlayerData winnerPlayerData = winner.getPlayerData();
            Assertions.assertEquals(bonus, winnerPlayerData.getRatingPoints());
        }
    }
}