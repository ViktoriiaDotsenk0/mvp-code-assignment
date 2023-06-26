package com.skai.mvpassignment.service;

import java.io.File;

public interface ScoreCounterProvider {
    ScoreCounter getScoreCounter(File file);
}
