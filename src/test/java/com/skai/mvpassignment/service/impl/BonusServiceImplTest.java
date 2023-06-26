package com.skai.mvpassignment.service.impl;

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
    public void testAddWinnersBonuses_MultipleWinners_UpdatedScoresWithBonus() {
        // Given
        List<PlayerStats> winners = getPlayersStatsList();
        int bonus = 10;
        underTest.setBonuses(bonus);
        // When
        underTest.addWinnersBonuses(winners);

        // Then
        for (PlayerStats winnerStats : winners) {
            PlayerData winnerPlayerData = winnerStats.getPlayerData();
            Assertions.assertEquals(bonus, winnerPlayerData.getRatingPoints());
        }
    }

    private List<PlayerStats> getPlayersStatsList() {
        BasketballPlayerStats player1 = new BasketballPlayerStats("name1", "nick1", "12", "Team A", 10, 12, 8, null);
        HandballPlayerStats player2 = new HandballPlayerStats("name2", "nick2", "13", "Team B", 12, 0, null);
        return Arrays.asList(player1, player2);
    }
}