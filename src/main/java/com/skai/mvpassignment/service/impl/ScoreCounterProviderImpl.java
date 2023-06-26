package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.exceptions.ErrorLogger;
import com.skai.mvpassignment.service.ScoreCounter;
import com.skai.mvpassignment.service.ScoreCounterProvider;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScoreCounterProviderImpl implements ScoreCounterProvider {
    private final Map<String, List<ScoreCounter>> scoreCounterMap;

    @Autowired
    public ScoreCounterProviderImpl(List<ScoreCounter> scoreCounters) {
        this.scoreCounterMap = createScoreCounterMap(scoreCounters);
    }

    private Map<String, List<ScoreCounter>> createScoreCounterMap(List<ScoreCounter> scoreCounters) {
        Map<String, List<ScoreCounter>> scoreCounterMap = new HashMap<>();
        for (ScoreCounter scoreCounter : scoreCounters) {
            scoreCounterMap.computeIfAbsent(scoreCounter.getGameName(), k -> new ArrayList<>());
            scoreCounterMap.get(scoreCounter.getGameName()).add(scoreCounter);
        }
        return scoreCounterMap;
    }

    @Override
    public ScoreCounter getScoreCounter(File file) {
        String gameName = getFirstString(file);
        return getScoreCounter(gameName, file.getName());
    }

    private ScoreCounter getScoreCounter(String gameName, String filename) {
        List<ScoreCounter> scoreCounters = scoreCounterMap.get(gameName);
        if (scoreCounters!= null){
            String fileExtension = FilenameUtils.getExtension(filename);
            for (ScoreCounter scoreCounter : scoreCounters) {
                if (scoreCounter.getFileExtension().equals(fileExtension)) {
                    return scoreCounter;
                }
            }
        }
        ErrorLogger.logAndExit("Game file is not supported: " + filename);
        return null;
    }

    private String getFirstString(File gameStatsFile) {
        if (gameStatsFile.isDirectory())
            ErrorLogger.logAndExit("Game file cannot be a directory: "+ gameStatsFile.getPath());
        try (BufferedReader br = new BufferedReader(new FileReader(gameStatsFile.getPath()))) {
            String firstLine = br.readLine();
            if (firstLine == null || firstLine.equals(""))
                ErrorLogger.logAndExit("Game name is not determined in file: " + gameStatsFile.getPath());
            return firstLine;
        } catch (Exception e) {
            ErrorLogger.logAndExit(e.getMessage());
        }
        return null;
    }
}
