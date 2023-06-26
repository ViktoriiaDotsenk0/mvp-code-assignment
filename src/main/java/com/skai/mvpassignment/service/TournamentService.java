package com.skai.mvpassignment.service;

import com.skai.mvpassignment.model.PlayerData;

import java.io.File;
import java.util.List;

public interface TournamentService {
    List<PlayerData> getTournamentPlayers(File gameDir);

    List<PlayerData> getMVP(List<PlayerData> players);
}
