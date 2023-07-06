package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.model.GameResult;
import com.skai.mvpassignment.model.PlayerData;
import com.skai.mvpassignment.model.statistics.BasketballPlayerStats;
import com.skai.mvpassignment.model.statistics.HandballPlayerStats;
import com.skai.mvpassignment.model.statistics.PlayerStats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(properties={
        "game.winner.bonus=10"})
class BonusServiceImplTest {

    @Autowired
    private BonusServiceImpl underTest;

    @Test
    public void testAddBonuses() {
        // Given
        BasketballPlayerStats player1 = new BasketballPlayerStats("name1", "nick1", "12", "Team A", 10, 12, 8);
        HandballPlayerStats player2 = new HandballPlayerStats("name2", "nick2", "13", "Team B", 12, 0);
        List<GameResult> winners = Arrays.asList(new GameResult(player1,0), new GameResult(player2,0));
        // When
        List<PlayerData> winnersWithBonuses = underTest.addBonuses(winners);

        // Then
        for (PlayerData winner : winnersWithBonuses) {
            Assertions.assertEquals(10, winner.getRatingPoints());
        }
    }
}