package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.exceptions.ValidationException;
import com.skai.mvpassignment.model.statistics.PlayerStats;
import com.skai.mvpassignment.service.PlayersStatsValidator;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PlayerStatsValidatorImpl implements PlayersStatsValidator {
    @Override
    public void validate(List<? extends PlayerStats> playersStats, String path) throws ValidationException {
        checkIfPresent(playersStats, path);
        validateForDuplicates(playersStats, path);
    }

    private void checkIfPresent(List<? extends PlayerStats> playersStats, String path) throws ValidationException {
        if (playersStats == null || playersStats.isEmpty())
            throw new ValidationException("Players stats were not found in file: " + path);
    }

    private void validateForDuplicates(List<? extends PlayerStats> playersStats, String path) throws ValidationException {
        Set<String> nickNames = new HashSet<>();
        Set<String> numbers = new HashSet<>();

        boolean hasDuplicates = playersStats.stream()
                .anyMatch(playerStats ->
                        !nickNames.add(playerStats.getNick()) || !numbers.add(playerStats.getNumber()));

        if (hasDuplicates) {
            throw new ValidationException("Not unique player stats in game file: " + path);
        }
    }
}
