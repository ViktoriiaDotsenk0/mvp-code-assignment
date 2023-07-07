package com.skai.mvpassignment.service.impl.handball;

import com.skai.mvpassignment.model.statistics.HandballPlayerStats;
import com.skai.mvpassignment.service.StatsCalculator;
import com.skai.mvpassignment.service.StatsManager;
import lombok.Value;
import org.springframework.stereotype.Service;

@Service
@Value
public class HandballStatsManager implements StatsManager<HandballPlayerStats> {
    String gameName = "HANDBALL";
    Class<HandballPlayerStats> type = HandballPlayerStats.class;
    StatsCalculator<HandballPlayerStats> statsCalculator;
}
