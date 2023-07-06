package com.skai.mvpassignment.service;

import java.io.File;

public interface StatsManagerProvider {
    StatsManager<?> getStatsManager(File file);
}
