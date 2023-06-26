package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.exceptions.ErrorLogger;
import com.skai.mvpassignment.model.PlayerData;
import com.skai.mvpassignment.service.ScoreCounter;
import com.skai.mvpassignment.service.ScoreCounterProvider;
import com.skai.mvpassignment.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {
    private final ScoreCounterProvider scoreCounterProvider;

    @Override
    public List<PlayerData> getTournamentPlayers(File gameDir) {
        Map<String, PlayerData> tournamentPlayers = new HashMap<>();

        File[] gamesStatsFiles = getGameFiles(gameDir);
        for (File gameFile : gamesStatsFiles) {
            ScoreCounter scoreCounter = scoreCounterProvider.getScoreCounter(gameFile);
            List<PlayerData> gamePlayers = scoreCounter.countPlayersScore(gameFile);
            for (PlayerData gamePlayer : gamePlayers) {
                PlayerData tournamentParticipant = tournamentPlayers.get(gamePlayer.getNick());
                if (tournamentParticipant == null)
                    tournamentPlayers.put(gamePlayer.getNick(), gamePlayer);
                else tournamentParticipant.setRatingPoints(
                        tournamentParticipant.getRatingPoints() + gamePlayer.getRatingPoints());
            }
        }
        return tournamentPlayers
                .values()
                .stream()
                .toList();
    }

    @Override
    public List<PlayerData> getMVP(List<PlayerData> players) {
        if (players == null || players.isEmpty())
            ErrorLogger.logAndExit("Failed to getMvp. Players list is null or empty");
        List<PlayerData> sortedPlayers = sortPlayersByScore(players);
        List<PlayerData> mvpList = new ArrayList<>();
        Integer bestScore = sortedPlayers.get(sortedPlayers.size() - 1).getRatingPoints();
        for (int i = sortedPlayers.size() - 1; i >= 0; i--) {
            if (sortedPlayers.get(i).getRatingPoints().equals(bestScore))
                mvpList.add(sortedPlayers.get(i));
            else break;
        }
        return mvpList;
    }

    private List<PlayerData> sortPlayersByScore(List<PlayerData> players) {
        return players.stream()
                .sorted(Comparator.comparingInt(PlayerData::getRatingPoints))
                .collect(Collectors.toList());
    }

    private File[] getGameFiles(File gameDir) {
        File[] files = gameDir.listFiles();
        if (files == null || files.length == 0) {
            ErrorLogger.logAndExit("Game files folder is empty or doesn't exist: " + gameDir.getPath());
        }
        return files;
    }
}
