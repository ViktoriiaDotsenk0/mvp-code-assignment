package com.skai.mvpassignment.service.impl.basketball;

import com.skai.mvpassignment.model.statistics.BasketballPlayerStats;
import com.skai.mvpassignment.service.StatsCalculator;
import com.skai.mvpassignment.service.StatsManager;
import lombok.Value;
import org.springframework.stereotype.Service;

@Service
@Value
public class BasketballStatsManager implements StatsManager<BasketballPlayerStats> {
    String gameName = "BASKETBALL";
    Class<BasketballPlayerStats> type = BasketballPlayerStats.class;
    StatsCalculator<BasketballPlayerStats> statsCalculator;
}
