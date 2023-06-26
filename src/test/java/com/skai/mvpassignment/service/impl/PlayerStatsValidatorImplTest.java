package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.exceptions.ValidationException;
import com.skai.mvpassignment.model.statistics.HandballPlayerStats;
import com.skai.mvpassignment.model.statistics.PlayerStats;
import com.skai.mvpassignment.service.PlayersStatsValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class PlayerStatsValidatorImplTest {

    private PlayersStatsValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new PlayerStatsValidatorImpl();
    }

    @Test
    public void testValidationWithNullAndEmptyList() {
        Assertions.assertThrows(ValidationException.class, () -> validator.validate(null, ""));
        Assertions.assertThrows(ValidationException.class, () -> validator.validate(new ArrayList<>(), ""));
    }

    @Test
    public void testValidationWithDuplicates() {
        // Given
        List<PlayerStats> playerStatsWithDuplicatedNicks = getPlayerStatsWithDuplicatedNicks();
        List<PlayerStats> playerStatsWithDuplicatedNumbers = getPlayerStatsWithDuplicatedNumbers();

        // Then
        Assertions.assertThrows(ValidationException.class, () -> validator.validate(playerStatsWithDuplicatedNicks, ""));
        Assertions.assertThrows(ValidationException.class, () -> validator.validate(playerStatsWithDuplicatedNumbers, ""));
    }

    @Test
    public void testValidationWithCorrectData() {
        // Given
        List<PlayerStats> playersStats = getPlayerStats();

        //Then
        Assertions.assertDoesNotThrow(() -> validator.validate(playersStats, ""));
    }

    private List<PlayerStats> getPlayerStatsWithDuplicatedNicks() {
        List<PlayerStats> playerStats = getPlayerStats();
        playerStats.get(0).getPlayerData().setNick(playerStats.get(1).getPlayerData().getNick());
        return playerStats;
    }
    private List<PlayerStats> getPlayerStatsWithDuplicatedNumbers() {
        List<PlayerStats> playerStats = getPlayerStats();
        playerStats.get(0).getPlayerData().setNumber(playerStats.get(1).getPlayerData().getNumber());
        return playerStats;
    }
    private List<PlayerStats> getPlayerStats() {
        HandballPlayerStats player1 = new HandballPlayerStats("name1","nick1", "12","Team A", 10, 12, null);
        HandballPlayerStats player2 = new HandballPlayerStats("name2","nick2", "13","Team B", 12, 15, null);
        return Arrays.asList(player1, player2);
    }
}