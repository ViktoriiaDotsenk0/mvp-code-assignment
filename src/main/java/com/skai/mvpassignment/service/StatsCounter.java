package com.skai.mvpassignment.service;

import com.skai.mvpassignment.model.GameResult;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface StatsCounter {
    Map<String, List<GameResult>> calculateGameResults(File file);
}
