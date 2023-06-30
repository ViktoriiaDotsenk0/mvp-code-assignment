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
        List<PlayerStats> playerStatsWithDuplicatedNicks = List.of(
                HandballPlayerStats.builder().nick("nick1").number("12").build(),
                HandballPlayerStats.builder().nick("nick1").number("13").build());

        List<PlayerStats> playerStatsWithDuplicatedNumbers = List.of(
                HandballPlayerStats.builder().nick("nick1").number("12").build(),
                HandballPlayerStats.builder().nick("nick2").number("12").build());

        // Then
        Assertions.assertThrows(ValidationException.class, () -> validator.validate(playerStatsWithDuplicatedNicks, ""));
        Assertions.assertThrows(ValidationException.class, () -> validator.validate(playerStatsWithDuplicatedNumbers, ""));
    }

    @Test
    public void testValidationWithCorrectData() {
        // Given
        List<PlayerStats> playersStats = List.of(
                HandballPlayerStats.builder().nick("nick1").number("12").build(),
                HandballPlayerStats.builder().nick("nick2").number("13").build());
        //Then
        Assertions.assertDoesNotThrow(() -> validator.validate(playersStats, ""));
    }
}