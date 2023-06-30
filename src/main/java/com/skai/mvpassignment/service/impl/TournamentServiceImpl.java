package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.model.PlayerData;
import com.skai.mvpassignment.service.AwardService;
import com.skai.mvpassignment.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {
    private final AwardService awardService;

    @Override
    public List<PlayerData> getTournamentPlayers(File gameDir) {
        return Arrays.stream(getGameFiles(gameDir))
                .flatMap(gameFile -> awardService.getPlayersWithBonuses(gameFile).stream())
                .collect(Collectors.toMap(
                        PlayerData::getNick,
                        Function.identity(),
                        (existingPlayer, newPlayer) -> {
                            existingPlayer.setRatingPoints(existingPlayer.getRatingPoints() + newPlayer.getRatingPoints());
                            return existingPlayer;
                        }))
                .values()
                .stream()
                .toList();
    }

    @Override
    public List<PlayerData> getMVP(List<PlayerData> players) {
        if (players == null || players.isEmpty())
            throw new IllegalArgumentException("Failed to getMvp. Players list is null or empty");
        return players.stream()
                .collect(Collectors.groupingBy(PlayerData::getRatingPoints))
                .entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElseThrow(()-> new RuntimeException("Failed to find MVP"));
    }

    private File[] getGameFiles(File gameDir) {
        File[] files = gameDir.listFiles();
        if (files == null || files.length == 0) {
           throw new IllegalStateException("Game folder is empty");
        }
        return files;
    }
}
