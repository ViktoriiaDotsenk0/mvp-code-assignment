package com.skai.mvpassignment.service;

import com.skai.mvpassignment.model.PlayerData;

import java.io.File;
import java.util.List;

public interface AwardService {
    List<PlayerData> getPlayersWithBonuses(File gameFile);
}
