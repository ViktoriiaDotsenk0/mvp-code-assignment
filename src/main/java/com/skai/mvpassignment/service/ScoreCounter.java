package com.skai.mvpassignment.service;

import com.skai.mvpassignment.model.PlayerData;

import java.io.File;
import java.util.List;

public abstract class ScoreCounter {
    private final String gameName;
    private final String fileExtension;

    protected ScoreCounter(String gameName, String fileExtension) {
        this.gameName = gameName;
        this.fileExtension = fileExtension;
    }

    public abstract List<PlayerData> countPlayersScore(File gameFile);

    public String getGameName() {
        return gameName;
    }

    public String getFileExtension() {
        return fileExtension;
    }
}
