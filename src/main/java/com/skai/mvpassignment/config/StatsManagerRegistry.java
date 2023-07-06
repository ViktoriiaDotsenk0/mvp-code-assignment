package com.skai.mvpassignment.config;

import com.skai.mvpassignment.model.statistics.BasketballPlayerStats;
import com.skai.mvpassignment.model.statistics.HandballPlayerStats;
import com.skai.mvpassignment.service.StatsManager;
import com.skai.mvpassignment.service.StatsCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatsManagerRegistry {

    @Bean
    public StatsManager<BasketballPlayerStats> basketballPlayerStatsCalculator(StatsCalculator<BasketballPlayerStats> statsManager){
        return new StatsManager<>("BASKETBALL", BasketballPlayerStats.class, statsManager);
    }

    @Bean
    public StatsManager<HandballPlayerStats> handballPlayerStatsManager(StatsCalculator<HandballPlayerStats> statsManager){
        return new StatsManager<>("HANDBALL", HandballPlayerStats.class, statsManager);
    }
}
